package com.example.MovieReviewBoard.Business

import com.example.MovieReviewBoard.model.TopicAccess
import com.example.MovieReviewBoard.model.TopicMessages
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class TopicConsumer:ITopicConsumer {
    override fun getMessages(topicAccess:TopicAccess): List<TopicMessages>? {
        try {
            val consumer = createConsumer(topicAccess)

            val topicPartitions = consumer.partitionsFor(topicAccess.topicName).map { TopicPartition(it.topic(), it.partition()) }
            consumer.assign(topicPartitions)
            consumer.seekToBeginning(consumer.assignment())

            val endOffsets = consumer.endOffsets(consumer.assignment())
            fun pendingMessages() = endOffsets.any { consumer.position(it.key) < it.value }

            var records = consumer.poll(Duration.ofSeconds(1))


//            consumer.subscribe(listOf("priv.motor.carfactory.topicstackfeeder.prueba"))//(listOf(topicAccess.topicName))
//            consumer.seekToBeginning(consumer.assignment())
//            consumer.beginningOffsets(consumer.assignment())
//            val records = consumer.poll(Duration.ofSeconds(0))
//
            return records.map{TopicMessages(it.value())}

        }catch (e:Exception){
            println(e.message)
            return null//e.message?.let { listOf(TopicMessages(it))}
        }
    }

    private fun createConsumer(topicAccess:TopicAccess): Consumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = "kafka-greensilence.storage.mpi-internal.com:9094"//topicAccess.server
        //props["group.id"] = UUID.randomUUID().toString()//"topic-processor" //grupo de consumers
        props[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        props[ConsumerConfig.CLIENT_ID_CONFIG] =  "your_client_id"
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] =  "earliest"
        props["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        props["sasl.mechanism"] = "SCRAM-SHA-256"
        props["security.protocol"] = "SASL_SSL"
        props["sasl.jaas.config"] = "org.apache.kafka.common.security.scram.ScramLoginModule required username='ariel.grillo' password='k0hRICuwcZnyfWUogsETjV4BbKg4NrmV';"
        //props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
        //props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        return KafkaConsumer<String, String>(props)
    }

}