package com.example.MovieReviewBoard.web

import com.example.MovieReviewBoard.Utils.Constants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constants.URL_BASE_HELLOWORLD)
class HelloWorldController {
    @GetMapping("")
    fun GetHelloWorld(): ResponseEntity<String> {
        return ResponseEntity("Hello World !",HttpStatus.OK)
    }
}