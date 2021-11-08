package com.example.MovieReviewBoard.Business

import com.example.MovieReviewBoard.model.TopicAccess
import com.example.MovieReviewBoard.model.TopicMessages
import com.example.MovieReviewBoard.model.TopicRecord
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class TopicConsumer:ITopicConsumer {
    override fun getMessages(topicAccess:TopicAccess): List<TopicMessages>? {
        return try {
            val consumer = createConsumer(topicAccess)
            var results= readMessages(topicAccess.topicName,consumer)

            results.map{TopicMessages(it)}

        }catch (e:Exception){
            println(e.message)
            null
        }
    }

    private fun createConsumer(topicAccess:TopicAccess): Consumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = topicAccess.server
        props[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        props[ConsumerConfig.CLIENT_ID_CONFIG] =  "client_id"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] =  "earliest"
        props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["sasl.mechanism"] = "SCRAM-SHA-256"
        props["security.protocol"] = "SASL_SSL"
        props["sasl.jaas.config"] = "org.apache.kafka.common.security.scram.ScramLoginModule required username='${topicAccess.user}' password='${topicAccess.pass}';" //k0hRICuwcZnyfWUogsETjV4BbKg4NrmV
        return KafkaConsumer<String, String>(props)
    }
    private fun readMessages(topicName: String, consumer: Consumer<String,String>): MutableList<TopicRecord>{

        val topicPartitions = consumer.partitionsFor(topicName).map { TopicPartition(it.topic(), it.partition()) }
        consumer.assign(topicPartitions)
        consumer.seekToBeginning(consumer.assignment())

        var records: ConsumerRecords<String, String>
        val results= mutableListOf<TopicRecord>()
        val endOffsets = consumer.endOffsets(consumer.assignment())
        fun pendingMessages() = endOffsets.any { consumer.position(it.key) < it.value }

        do {
            records = consumer.poll(Duration.ofMillis(1000))
            results.addAll(records.map{TopicRecord(it.key(),it.value(),it.offset(),it.partition(),it.topic())})
        } while(pendingMessages())
        return results
    }
}