package com.example.NotificationService.Adapter;

import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Request.SmsRequest;
import com.example.NotificationService.Response.PostReqResponse;

import java.time.LocalDateTime;

public class SmsAdapter {

    public static SmsEntity createEntity(SmsRequest smsRequest)
    {
        SmsEntity smsEntity =new SmsEntity();
        smsEntity.setPno(smsRequest.getPno());
        smsEntity.setMsg(smsRequest.getMsg());
        smsEntity.setCreated_at(LocalDateTime.now());
        smsEntity.setUpdated_at(LocalDateTime.now());
        return smsEntity;
    }
    public static PostReqResponse createResponse(SmsEntity smsEntity)
    {
        PostReqResponse postReqResponse = new PostReqResponse();
        postReqResponse.setPno(smsEntity.getPno());
        postReqResponse.setMsg(smsEntity.getMsg());
        PostReqResponse.Data data=new PostReqResponse.Data();
        data.setReq_id(smsEntity.getId());
        data.setComments("successfully sent");
        return postReqResponse;
    }
}
