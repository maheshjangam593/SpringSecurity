/*
package com.example.springsecuritypractice.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {


    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {

        return new ApiInfo("JWT APPlication", "About Security", "1.0", "Terms& C",
                new Contact("mahesh", "https://www.google.com", "mahesh@gmail.com"), "License Of APIs", "API Licenses",
                Collections.EMPTY_LIST);
    }

}
*/
