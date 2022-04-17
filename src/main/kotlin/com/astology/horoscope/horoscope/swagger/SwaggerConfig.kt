package com.astology.horoscope.horoscope.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.service.Contact

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Spring Boot2.X Kotlin Use in Swagger2 structure RESTFul APIs")
            .description("More SpringBoot2.X Kotlin Pay attention to the article:Cherish fish blog")
            .termsOfServiceUrl("https://www.google.com")
            .contact(Contact("Cherish fish", "https://www.google.com", "abhi.cs005@gmail.com"))
            .version("1.0.0")
            .build()
    }
}