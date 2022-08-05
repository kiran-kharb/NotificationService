package com.example.NotificationService.service;


import com.example.NotificationService.Entities.BlacklistEntity;
import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Entities.ThirdPartyEntity;
import com.example.NotificationService.Repository.BlacklistRepository;
import com.example.NotificationService.Repository.SmsRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@Data
public class KafkaConsumer {

    RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private final SmsRepository smsrepository;
    @Autowired
    private final BlacklistRepository blacklistRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String reqID) {
        LOGGER.info(String.format("Json message recieved-> %s"), reqID);
        //get details from database
        SmsEntity smsEntity = getDetails(reqID);
        String phoneNumber = smsEntity.getPno();
        Optional<BlacklistEntity> blacklistEntity = Optional.ofNullable(blacklistRepository.findByID(phoneNumber));
        if (!blacklistEntity.isPresent())
        {
            log.error("PHONE NUMBER IS BLACKLISTED");

        }
        else
        {
            ThirdPartyEntity thirdPartyEntity= getThirdPartyDetails(smsEntity);
            System.out.println(callThirdPartyApi(thirdPartyEntity));

        }



    }
    public ThirdPartyEntity getThirdPartyDetails(SmsEntity smsEntity)
    {

        ThirdPartyEntity.Channels channels=new ThirdPartyEntity.Channels();
        ThirdPartyEntity.Channels.Sms sms=new ThirdPartyEntity.Channels.Sms();
        sms.setText(smsEntity.getMsg());
        ThirdPartyEntity.Destination destination=new ThirdPartyEntity.Destination();
        destination.setMsidn(Collections.singletonList(smsEntity.getPno()));
        destination.setCorrelationId(String.valueOf(smsEntity.getId()));

        ThirdPartyEntity thirdPartyEntity=new ThirdPartyEntity();
        thirdPartyEntity.setDeliveryChannel("sms");
        thirdPartyEntity.setChannels(channels);
        thirdPartyEntity.setDestination(Collections.singletonList(destination));

        return thirdPartyEntity;
    }
    public ThirdPartyEntity callThirdPartyApi(ThirdPartyEntity thirdPartyEntity)
    {
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("key", "93ceffda-5941-11ea-9da9-025282c394f2");
        HttpEntity<ThirdPartyEntity> entity = new HttpEntity<ThirdPartyEntity>(thirdPartyEntity,headers);
        String url="https://api.imiconnect.in/resources/v1/messaging";
        ResponseEntity<ThirdPartyEntity> result=restTemplate.exchange(url, HttpMethod.POST,entity,ThirdPartyEntity.class);

        return result.getBody();
    }
    public SmsEntity getDetails(String reqId)
    {

        Optional<SmsEntity> smsEntity =smsrepository.findById(Integer.valueOf(reqId));

        SmsEntity smsEntity1 = smsEntity.get();
        return smsEntity1;
    }
}
