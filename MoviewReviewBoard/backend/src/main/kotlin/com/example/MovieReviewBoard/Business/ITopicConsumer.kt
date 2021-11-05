package com.example.MovieReviewBoard.Business

import com.example.MovieReviewBoard.model.TopicAccess
import com.example.MovieReviewBoard.model.TopicMessages

interface ITopicConsumer {
    fun getMessages(topicAccess:TopicAccess):List<TopicMessages>?
}