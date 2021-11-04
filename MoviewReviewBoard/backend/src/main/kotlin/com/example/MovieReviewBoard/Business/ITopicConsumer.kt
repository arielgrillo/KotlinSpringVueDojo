package com.example.MovieReviewBoard.Business

import com.example.MovieReviewBoard.model.TopicMessages

interface ITopicConsumer {
    fun getMessages():List<TopicMessages>
}