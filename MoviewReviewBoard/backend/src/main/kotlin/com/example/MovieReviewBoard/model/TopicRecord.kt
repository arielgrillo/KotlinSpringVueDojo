package com.example.MovieReviewBoard.model

data class TopicRecord (
    val key: String,
    val value: String,
    val offset: Long,
    val partition: Int,
    val topic: String
)