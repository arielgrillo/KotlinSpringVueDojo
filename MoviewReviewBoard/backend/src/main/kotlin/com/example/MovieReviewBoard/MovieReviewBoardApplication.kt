package com.example.MovieReviewBoard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableResourceServer
class MovieReviewBoardApplication

fun main(args: Array<String>) {
	runApplication<MovieReviewBoardApplication>(*args)
}
