package com.example.NotificationService.service;


import com.example.NotificationService.Entities.*;
import com.example.NotificationService.Repository.BlacklistRepository;
import com.example.NotificationService.Repository.SmsRepository;
import com.example.NotificationService.Response.ThirdPartyResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@Data
public class KafkaConsumer {

   private  RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private final SmsRepository smsrepository;
    @Autowired
    private final BlacklistRepository blacklistRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(int reqID) {
        //LOGGER.info(String.format("Json message recieved-> %s"), reqID);
        //get details from database
        SmsEntity smsEntity = getDetails(reqID);
        String phoneNumber = smsEntity.getPno();
        Optional<BlacklistEntity> blacklistEntity = Optional.ofNullable(blacklistRepository.findByID(phoneNumber));
        if (blacklistEntity.isPresent())
        {
            log.error("PHONE NUMBER IS BLACKLISTED");

        }
        else
        {
            System.out.println("HEllo");
            ThirdPartyEntity thirdPartyEntity= getThirdPartyDetails(smsEntity);
            LOGGER.info(String.format("third party entity created"));
            System.out.println(thirdPartyEntity);
           ThirdPartyResponse thirdPartyResponse= callThirdPartyApi(thirdPartyEntity);
            LOGGER.info(String.format("Message consumed"));
            System.out.println(thirdPartyResponse);

        }



    }
    public ThirdPartyEntity getThirdPartyDetails(SmsEntity smsEntity)
    {
        ThirdPartyEntity thirdPartyEntity=new ThirdPartyEntity();

        Channels channels=new Channels();
        Sms sms=new Sms();
        sms.setText(smsEntity.getMsg());
        channels.setSms(sms);

        Destination destination=new Destination();
        destination.setCorrelationId(String.valueOf(smsEntity.getId()));
        destination.setMsisdn(Collections.singletonList(smsEntity.getPno()));

        thirdPartyEntity.setChannels(channels);
        thirdPartyEntity.setDestination(Collections.singletonList(destination));
        return thirdPartyEntity;
//        ThirdPartyEntity.Channels channels=new ThirdPartyEntity.Channels();
//
//        ThirdPartyEntity.Channels.Sms sms=new ThirdPartyEntity.Channels.Sms();
//        sms.setText(smsEntity.getMsg());
//        channels.setSms(sms);
//        ThirdPartyEntity.Destination destination=new ThirdPartyEntity.Destination();
//        destination.setMsisdn(Collections.singletonList(smsEntity.getPno()));
//        destination.setCorrelationId(String.valueOf(smsEntity.getId()));
//
//        ThirdPartyEntity thirdPartyEntity=new ThirdPartyEntity();
//        thirdPartyEntity.setDeliverychannel("sms");
//        thirdPartyEntity.setChannels(channels);
//        thirdPartyEntity.setDestination(Collections.singletonList(destination));
//
//        return thirdPartyEntity;
    }
    public ThirdPartyResponse callThirdPartyApi(ThirdPartyEntity thirdPartyEntity)
    {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("key","93ceffda-5941-11ea-9da9-025282c394f2");

        HttpEntity<Object> requestEntity = new HttpEntity<>(thirdPartyEntity, headers);

        ResponseEntity<ThirdPartyResponse> responseEntity = restTemplate.exchange("https://api.imiconnect.in/resources/v1/messaging",
                HttpMethod.POST,
                requestEntity,
                ThirdPartyResponse.class);

        return responseEntity.getBody();
//        HttpHeaders headers=new HttpHeaders();
//        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("key", "93ceffda-5941-11ea-9da9-025282c394f2");
//        HttpEntity<ThirdPartyEntity> entity = new HttpEntity<ThirdPartyEntity>(thirdPartyEntity,headers);
//        LOGGER.info(String.format("error 1"));
//        String url="https://api.imiconnect.in/resources/v1/messaging";
//        ResponseEntity<ThirdPartyResponse> result=restTemplate.exchange(
//                url, HttpMethod.POST,entity,ThirdPartyResponse.class);
//        LOGGER.info(String.format(" error 2"));
//        return result.getBody();
    }
    public SmsEntity getDetails(int reqId)
    {

        Optional<SmsEntity> smsEntity =smsrepository.findById(reqId);

        SmsEntity smsEntity1 = smsEntity.get();
        return smsEntity1;
    }
}
