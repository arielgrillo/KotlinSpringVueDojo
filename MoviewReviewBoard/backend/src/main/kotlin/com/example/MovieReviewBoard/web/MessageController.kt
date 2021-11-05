package com.example.MovieReviewBoard.web

import com.example.MovieReviewBoard.Business.ITopicConsumer
import com.example.MovieReviewBoard.Business.TopicConsumer
import com.example.MovieReviewBoard.Utils.Constants
import com.example.MovieReviewBoard.model.TopicAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Constants.URL_BASE_MESSAGE)
class MessageController {

    @Autowired
    val topicConsumer: ITopicConsumer?=null

    @PostMapping("/GetMessagesFromTopic")
    fun getMessages(@RequestBody topicAccess:TopicAccess):ResponseEntity<Any>{
        return ResponseEntity(topicConsumer?.getMessages(topicAccess),HttpStatus.OK)
    }
}