package com.example.NotificationService.service;


import com.example.NotificationService.Adapter.SmsAdapter;
import com.example.NotificationService.Entities.*;
import com.example.NotificationService.Repository.BlacklistRepository;
import com.example.NotificationService.Repository.SearchRepository;
import com.example.NotificationService.Repository.SmsRepository;
import com.example.NotificationService.Response.ListThirdPartyResponse;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Data
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private final SmsRepository smsrepository;
    @Autowired
    private final BlacklistRepository blacklistRepository;

    @Autowired
    private final SearchRepository searchRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(int reqID) {
        //LOGGER.info(String.format("Json message recieved-> %s"), reqID);
        //get details from database
        SmsEntity smsEntity = getDetails(reqID);
        String phoneNumber = smsEntity.getPhoneNumber();
        Optional<BlacklistEntity> blacklistEntity = Optional.ofNullable(blacklistRepository.findByID(phoneNumber));
        if (blacklistEntity.isPresent())
        {
            log.error("PHONE NUMBER IS BLACKLISTED");

        }
        else
        {

            ThirdPartyEntity thirdPartyEntity= getThirdPartyDetails(smsEntity);
            LOGGER.info(String.format("third party entity created"));
            System.out.println(thirdPartyEntity);

            ListThirdPartyResponse thirdPartyResponseList= callThirdPartyApi(thirdPartyEntity);
            List<ThirdPartyResponse> thirdPartyResponses= thirdPartyResponseList.getThirdPartyResponseList();
            ThirdPartyResponse thirdPartyResponse=thirdPartyResponses.get(0);
            LOGGER.info(String.format("Message consumed"));
            System.out.println(thirdPartyResponseList);
            if(thirdPartyResponse.getCode().equals("1001") )
            {
                smsEntity.setStatus("Processed Successfully");
                smsEntity.setUpdatedAt(LocalDateTime.now());
                smsrepository.save(smsEntity);
            }
            else
            {
                smsEntity.setStatus("Message Sending Failed");
                smsEntity.setUpdatedAt(LocalDateTime.now());
                smsrepository.save(smsEntity);
            }
            //indexing the details
            SearchEntity searchEntity= SmsAdapter.createSearchEntity(smsEntity);
            System.out.println(searchEntity);
            searchRepository.save(searchEntity);
            LOGGER.info("indexing successfull");

        }

    }
    public ThirdPartyEntity getThirdPartyDetails(SmsEntity smsEntity)
    {
        ThirdPartyEntity thirdPartyEntity=new ThirdPartyEntity();

        Channels channels=new Channels();
        Sms sms=new Sms();
        sms.setText(smsEntity.getMessage());
        channels.setSms(sms);

        Destination destination=new Destination();
        destination.setCorrelationId(String.valueOf(smsEntity.getRequestId() ));
        destination.setMsisdn(Collections.singletonList(smsEntity.getPhoneNumber()));

        thirdPartyEntity.setChannels(channels);
        thirdPartyEntity.setDestination(Collections.singletonList(destination));
        return thirdPartyEntity;

    }
    public ListThirdPartyResponse callThirdPartyApi(ThirdPartyEntity thirdPartyEntity)
    {
        RestTemplate restTemplate=new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("key","93ceffda-5941-11ea-9da9-025282c394f2");

        HttpEntity<List<ThirdPartyEntity>> requestEntity = new HttpEntity<>(Collections.singletonList(thirdPartyEntity), headers);

        ResponseEntity<ListThirdPartyResponse> responseEntity = restTemplate.exchange("https://api.imiconnect.in/resources/v1/messaging",
                HttpMethod.POST,
                requestEntity,
                ListThirdPartyResponse.class);

        return responseEntity.getBody();
    }
    public SmsEntity getDetails(int reqId)
    {

        Optional<SmsEntity> smsEntity =smsrepository.findById(reqId);

        SmsEntity smsEntity1 = smsEntity.get();
        return smsEntity1;
    }
}
