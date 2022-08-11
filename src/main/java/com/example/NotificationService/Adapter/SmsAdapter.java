package com.example.NotificationService.Adapter;

import com.example.NotificationService.Entities.SearchEntity;
import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Request.SmsRequest;
import com.example.NotificationService.Response.DataResponse;
import com.example.NotificationService.Response.ErrorResponse;
import com.example.NotificationService.Response.GenericResponse;

import java.time.LocalDateTime;

public class SmsAdapter {

    public static SmsEntity createEntity(SmsRequest smsRequest)
    {
        SmsEntity smsEntity =new SmsEntity();
        smsEntity.setPhoneNumber(smsRequest.getPno());
        smsEntity.setMessage(smsRequest.getMsg());
        smsEntity.setCreatedAt(LocalDateTime.now());
        smsEntity.setUpdatedAt(LocalDateTime.now());
        return smsEntity;
    }
    public static GenericResponse createResponse(SmsEntity smsEntity)
    {

        GenericResponse<DataResponse, ErrorResponse> genericResponse=new GenericResponse<>();
        DataResponse dataResponse=new DataResponse();
        dataResponse.setRequestId(String.valueOf(smsEntity.getRequestId() ));
        dataResponse.setComments("Successfully Sent!!");
        genericResponse.setData(dataResponse);
        return genericResponse;
    }
    public static SearchEntity createSearchEntity(SmsEntity smsEntity )
    {
        SearchEntity searchEntity=new SearchEntity();
        searchEntity.setId(smsEntity.getId());
        searchEntity.setPhoneNumber(smsEntity.getPhoneNumber());
        searchEntity.setMessage(smsEntity.getMessage());
        searchEntity.setCrAt(smsEntity.getCreatedAt());
        return searchEntity;
    }

    public static GenericResponse createResponseForGetId(SmsEntity smsEntity1) {
        GenericResponse<SmsEntity, ErrorResponse> genericResponse=new GenericResponse<>();

        genericResponse.setData(smsEntity1);
        return genericResponse;
    }
}
