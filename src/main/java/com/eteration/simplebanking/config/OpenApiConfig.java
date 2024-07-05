package com.eteration.simplebanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI simplebankingOpenAPI() {
    Contact contact = new Contact()
        .name("Eşref Özel")
        .url("https://github.com/esrefozel0/simplebanking/");

    Info info = new Info()
        .title("Simple Banking App API")
        .version("1.0.0")
        .description("Case Study - Simple Banking App API")
        .contact(contact);

    return new OpenAPI().info(info);
  }
}