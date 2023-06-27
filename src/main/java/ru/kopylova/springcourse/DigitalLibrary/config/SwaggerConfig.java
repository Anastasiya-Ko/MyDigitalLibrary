package ru.kopylova.springcourse.DigitalLibrary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Настройка конфигурации сваггера, который позволяет автоматически описывать API на основе его кода
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Library")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("anastasia2010kopylova@gmail.com")
                                                .name("Kopylova Anastasia")
                                )
                );
    }

}

