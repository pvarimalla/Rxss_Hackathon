package com.rx.savings.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@ServletComponentScan
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        HypermediaAutoConfiguration.class}
)
@ComponentScan("com.rx.savings.hackathon")
public class HackathonApplication {
    public static void main(String[] args) {
        SpringApplication.run(HackathonApplication.class, args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(512000000L));
        factory.setMaxRequestSize(DataSize.ofBytes(512000000L));
        return factory.createMultipartConfig();
    }
}
