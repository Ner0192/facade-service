# OpenTelemetry + Jaeger Integration with Spring Boot

This guide provides step-by-step instructions to configure OpenTelemetry Collector and Jaeger for tracing a Spring Boot application, along with custom logic to mask sensitive fields in responses.

---

## 📦 Prerequisites

- Docker & Docker Compose installed
- Java 17+ and Maven
- Spring Boot project
- `opentelemetry-javaagent.jar` (download from [OpenTelemetry Releases](https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases))

---

## ⚙️ Step 1: Configure OpenTelemetry Collector & Jaeger

### `otel-collector-config.yaml` loc `~/config/otlp/`
```yaml
receivers:
  otlp:
    protocols:
      grpc:
      http:
        endpoint: ":4318"

exporters:
  debug:
    verbosity: detailed
  otlphttp:
    endpoint: "http://jaeger:4318"

service:
  pipelines:
    traces:
      receivers: [otlp]
      exporters: [debug,otlphttp]
```

### `docker-compose.yaml`
```yaml
version: "3"
services:
  otel-collector:
    image: otel/opentelemetry-collector
    container_name: otel
    volumes:
      - /Users/a2241519/config/otlp/otel-collector-config.yaml:/etc/otel-collector-config.yaml
    command: ["--config", "/etc/otel-collector-config.yaml"]
    ports:
      - "4318:4318"
    depends_on:
      - jaeger
    networks:
      - otel-network

  jaeger:
    image: jaegertracing/all-in-one:latest
    container_name: jaeger
    environment:
      - COLLECTOR_OTLP_ENABLED=true
    ports:
      - "16686:16686"
    networks:
      - otel-network

networks:
  otel-network:
    driver: bridge
```

### 🚀 Start the services
```bash
docker-compose up -d
```

Jaeger UI will be available at [http://localhost:16686](http://localhost:16686)

---

## ☕ Step 2: Configure Spring Boot

### `pom.xml` Dependencies
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
  <groupId>io.opentelemetry</groupId>
  <artifactId>opentelemetry-sdk</artifactId>
  <version>1.35.0</version>
</dependency>
```

---

## 🌍 Step 3: Set Environment Variables

Set the following environment variables before starting the Spring Boot app:

```bash
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4318
export OTEL_RESOURCE_ATTRIBUTES=service.name=facade
export OTEL_TRACES_EXPORTER=otlp
export OTEL_EXPORTER_OTLP_TRACES_PROTOCOL=http/protobuf
export MANAGEMENT_OTLP_TRACING_ENDPOINT=http://localhost:4318/v1/traces
```

Optional (disable logs and metrics exporting):
```bash
export OTEL_LOGS_EXPORTER=none
export OTEL_METRICS_EXPORTER=none
```

---

## 🧪 Step 4: Run the Spring Boot App with Agent

```bash
java -javaagent:/path/to/opentelemetry-javaagent.jar -jar target/your-app.jar
```

---

## 🛡️ Step 5: Mask Sensitive Fields in Traces

### `JsonMasker.java`
```java
@Component
public class JsonMasker {
    private static final Logger log = LoggerFactory.getLogger(JsonMasker.class);

    @Value("${response.masked.fields:}")
    private String FIELDS;
    private Pattern FIELD_PATTERN;

    @PostConstruct
    public void init() {
        if (FIELDS == null || FIELDS.isBlank()) {
            FIELD_PATTERN = null; 
            log.warn("No fields configured for masking. Skipping masking.");
            return;
        }
        log.info("Masked fields property: " + FIELDS);
        FIELD_PATTERN = Pattern.compile(
                "\"(" + FIELDS.replaceAll(",", "|") + ")\"\\s*:\\s*(\".*?\"|\\d+(\\.\\d+)?|true|false|null)");
    }

    public String maskFields(String json, String maskChar) {
        if (FIELD_PATTERN == null) {
            return json;
        }
        Matcher matcher = FIELD_PATTERN.matcher(json);
        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            result.append(json, lastEnd, matcher.start(2));
            String originalValue = matcher.group(2);
            boolean quoted = originalValue.startsWith("\"");
            int length = quoted ? originalValue.length() - 2 : originalValue.length();
            String masked = maskChar.repeat(length);
            result.append("\"" + masked + "\"");
            lastEnd = matcher.end(2);
        }
        result.append(json.substring(lastEnd));
        log.info("Masked Response: "+result);
        return result.toString();
    }
}
```

### `JsonMaskingAdvice.java`
```java
@ControllerAdvice
public class JsonMaskingAdvice implements ResponseBodyAdvice<Object>, ClassFileTransformer {

    @Autowired
    private JsonMasker mask;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        try {
            String json = mask.maskFields(objectMapper.writeValueAsString(body), "*");

            Span span = Span.current();
            if (span != null && span.getSpanContext().isValid()) {
                span.setAttribute("response.body", json);
            }

            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return body;
        }
    }
}
```

### `application.properties`
```properties
response.masked.fields=password,ssn,email
```

---

## ✅ Verification

1. Hit any HTTP endpoint of your Spring Boot app.
2. Visit [http://localhost:16686](http://localhost:16686) to view traces.
3. Look for `response.body` attribute with masked fields in the trace spans.

---

## 📚 References

- [OpenTelemetry Java Agent](https://github.com/open-telemetry/opentelemetry-java-instrumentation)
- [Jaeger Documentation](https://www.jaegertracing.io/docs/)
- [Spring Boot Observability](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.export.otlp)
