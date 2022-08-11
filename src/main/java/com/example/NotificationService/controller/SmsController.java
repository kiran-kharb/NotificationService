package com.example.NotificationService.controller;

import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Exceptions.SmsException;
import com.example.NotificationService.Request.SmsRequest;
import com.example.NotificationService.Response.DataResponse;
import com.example.NotificationService.Response.ErrorResponse;
import com.example.NotificationService.Response.GenericResponse;
import com.example.NotificationService.service.SmsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GenericResponse> sendSms(@RequestBody SmsRequest smsrequest)
    {

        GenericResponse<DataResponse, ErrorResponse> genericResponse=new GenericResponse<>();
        try{
            genericResponse=smsservice.savesms(smsrequest);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        }
        catch(SmsException e)
        {
           // PostReqResponse.Error error = new PostReqResponse.Error();//this is the syntax to call static inner class
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setCode("INVALID_REQUEST");
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{request_id}")
    public ResponseEntity<GenericResponse> GetDetails(@PathVariable("request_id") String requestId)
    {
        GenericResponse<SmsEntity,ErrorResponse> genericResponse=new GenericResponse<>();

        try{
           genericResponse= smsservice.getsms(requestId);
           return new ResponseEntity<>(genericResponse,HttpStatus.OK);
        }
        catch(SmsException e)
        {
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setCode("request not found");
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse,HttpStatus.NOT_FOUND);

        }

    }

}
