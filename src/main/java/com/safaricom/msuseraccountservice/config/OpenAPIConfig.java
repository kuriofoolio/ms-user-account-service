package com.safaricom.msuseraccountservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @org.springframework.beans.factory.annotation.Value("${kuriag.openapi.dev-url}")
    private String devUrl;

    @org.springframework.beans.factory.annotation.Value("${kuriag.openapi.prod-url}")
    private String prodUrl;

    @Bean
    OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("lgithinji@safaricom.co.ke");
        contact.setName("Kuria Githinji");
        contact.setUrl("https://www.linkedin.com/in/kuria-githinji-1724b1218/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("User Account Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage user accounts.")
                .termsOfService("#")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
