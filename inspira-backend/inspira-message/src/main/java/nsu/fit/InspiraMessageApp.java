package nsu.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
        "nsu.fit.messaging.user",
        "nsu.fit.messaging.llm"
})
@SpringBootApplication
public class InspiraMessageApp {
    public static void main(String[] args) {
        SpringApplication.run(InspiraMessageApp.class, args);
    }
}
