package com.example.NotificationService.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class KafkaProducer {
    //@Value("${spring.kafka.topic.name}")
    private static final String topicName="smsService";

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    KafkaTemplate<Object, Integer> kafkaTemplate;

    public void sendMessage(int reqID){

        LOGGER.info(String.format("Message sent -> %s", reqID));

//         Message<String> message = MessageBuilder
//                .withPayload(reqID)
//                .setHeader(KafkaHeaders.TOPIC, topicName)
//                .build();
        //kafkaTemplate.se
        //kafkaTemplate.send(topicName,reqID);

        kafkaTemplate.send(topicName,reqID);
    }
}
