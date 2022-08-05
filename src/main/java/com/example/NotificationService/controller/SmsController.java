package com.example.NotificationService.controller;

import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Exceptions.SmsException;
import com.example.NotificationService.Request.SmsRequest;
import com.example.NotificationService.Response.PostReqResponse;
import com.example.NotificationService.service.SmsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Request to entity mapping
@RestController
@RequestMapping(path="v1/sms")
@Data
public class SmsController {

    @Autowired
    private final SmsService smsservice;


    @GetMapping
    public List<SmsEntity> getMsgs()
    {
        return smsservice.getMsgs();

    }
    @PostMapping
    public PostReqResponse sendSms(@RequestBody SmsRequest smsrequest)
    {
        PostReqResponse postReqResponse=new PostReqResponse();
        try{
            postReqResponse=smsservice.savesms(smsrequest);
        }
        catch(SmsException e)
        {
            PostReqResponse.Error error = new PostReqResponse.Error(); //this is the syntax to call static inner class
            error.setCode("INVALID_REQUEST");
            error.setMsg(e.getMessage());

        }

        return postReqResponse;
    }

    @GetMapping("/{request_id}")
    public PostReqResponse GetDetails(@PathVariable("request_id") String request_id)
    {
        return smsservice.getsms(request_id);
    }

}
