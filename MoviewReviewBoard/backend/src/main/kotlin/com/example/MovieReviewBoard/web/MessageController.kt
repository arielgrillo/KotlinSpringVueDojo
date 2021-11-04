package com.example.MovieReviewBoard.web

import com.example.MovieReviewBoard.Utils.Constants
import com.example.MovieReviewBoard.model.TopicAccess
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constants.URL_BASE_MESSAGE)
class MessageController {
    @PostMapping("/GetMessagesFromTopic")
    fun getMessages(@RequestBody topicAccess:TopicAccess):ResponseEntity<Any>{
        return ResponseEntity("get messagess from topic",HttpStatus.OK)
    }
}