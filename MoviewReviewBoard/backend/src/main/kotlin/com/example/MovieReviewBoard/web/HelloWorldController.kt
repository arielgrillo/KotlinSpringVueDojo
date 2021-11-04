package com.example.MovieReviewBoard.web

import com.example.MovieReviewBoard.Utils.Constants
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.*
import javax.servlet.Filter

@RestController
@RequestMapping(Constants.URL_BASE_HELLOWORLD)
class HelloWorldController {
//    @GetMapping("")
//    fun GetHelloWorld(): ResponseEntity<String> {
//        return ResponseEntity("Hello World !",HttpStatus.OK)
//    }

    @GetMapping("")
    fun GetHelloWorld(): String {
        return ("Hello World !")
    }

    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        // *** URL below needs to match the Vue client URL and port ***
        config.allowedOrigins = Collections.singletonList("http://localhost:8080")
        config.allowedMethods = Collections.singletonList("*")
        config.allowedHeaders = Collections.singletonList("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean<Filter>(CorsFilter(source))
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE)
        return bean
    }
}