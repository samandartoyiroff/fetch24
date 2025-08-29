package uz.tenzorsoft.fetch24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableAsync
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class Fetch24Application {

    public static void main(String[] args) {
        SpringApplication.run(Fetch24Application.class, args);
    }

}
