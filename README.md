## spring webflux

### 1. Dependencies

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
    <version>2.7.5</version>
</dependency>
```

### 2. Service

```java

@Service
public class StreamingService {

    private static final String FORMAT = "classpath:video/%s.mp4";

    private final ResourceLoader resourceLoader;

    public StreamingService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Mono<Resource> getVideo(String title) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(FORMAT, title)));
    }
}
```

### 3. Controller

```java

@SpringBootApplication
@RestController
public class DemoApplication {

    private final StreamingService streamingService;

    public DemoApplication(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @RequestMapping(value = "video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String ranger) {
        System.out.println("range in bytes() : " + ranger);

        return streamingService.getVideo(title);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```