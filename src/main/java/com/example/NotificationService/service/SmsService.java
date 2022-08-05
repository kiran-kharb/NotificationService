package com.example.NotificationService.service;

import com.example.NotificationService.Adapter.SmsAdapter;
import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Exceptions.SmsException;
import com.example.NotificationService.Repository.SmsRepository;
import com.example.NotificationService.Request.SmsRequest;
import com.example.NotificationService.Response.PostReqResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
@Data
public class SmsService {

    @Autowired
    private final SmsRepository smsrepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public List<SmsEntity> getMsgs()
    {
        return smsrepository.findAll();
//       return new SmsEntity(123 , "Hello World");
    }
    public PostReqResponse getsms(String request_id)
    {
        boolean b=smsrepository.existsById(Integer.valueOf(request_id));
        if(!b)
        {
            throw new SmsException("User not found");
        }
        Optional<SmsEntity> smsEntity =smsrepository.findById(Integer.valueOf(request_id));
//        if (smsEntity.isPresent()){
              SmsEntity smsEntity1 = smsEntity.get();
       // }
        return SmsAdapter.createResponse(smsEntity1);

    }
    public PostReqResponse savesms(SmsRequest smsrequest)
    {

        String pno = smsrequest.getPno();
        String msg = smsrequest.getMsg();
        //checkvalidity(pno , msg);
        SmsEntity smsentity= SmsAdapter.createEntity(smsrequest);

        smsrepository.save(smsentity);
        kafkaProducer.sendMessage(smsentity.getId());
        System.out.println("successfully published");
        return SmsAdapter.createResponse(smsentity);

    }
    private void checkvalidity(String pno , String msg)
    {
        if(pno.isEmpty())
            throw new SmsException("phone number can not be empty");

        if(!isValid(pno))
            throw new SmsException("phone number is not valid");
        if(msg.isEmpty())
            throw new SmsException("Message field is empty");
    }
    private boolean isValid(String pno)
    {
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(pno);
        return (m.find() && m.group().equals(pno));
    }


}
