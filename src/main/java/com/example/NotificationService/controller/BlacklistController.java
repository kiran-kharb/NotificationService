package com.example.NotificationService.controller;

import com.example.NotificationService.Entities.BlacklistEntity;
import com.example.NotificationService.Exceptions.SmsException;
import com.example.NotificationService.Request.BlacklistRequest;
import com.example.NotificationService.Response.ErrorResponse;
import com.example.NotificationService.Response.GenericResponse;
import com.example.NotificationService.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="/v1/blacklist")
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @GetMapping
    public List<BlacklistEntity> GetBlacklist() //fuctions begin wiht small
    {
        log.info("Hello");
      return  blacklistService.GetBlacklistNumbers();

    }

    @PostMapping
    public ResponseEntity<GenericResponse> BlacklistNumber(@RequestBody BlacklistRequest blacklistRequest)
    {
        GenericResponse<String, ErrorResponse> genericResponse=new GenericResponse<>();
        try{
            blacklistService.BlacklistNumbers(blacklistRequest);
            genericResponse.setData("Successfully blacklisted");
            return new ResponseEntity<>(genericResponse,HttpStatus.OK);
        }
        catch (SmsException e)
        {
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{phonenumber}")
    public ResponseEntity<GenericResponse> WhitelistNumbers(@PathVariable("phonenumber") String phonenumber)
    {
        GenericResponse<String,ErrorResponse> genericResponse=new GenericResponse<>();
        try{
            blacklistService.whitelist(phonenumber);
            genericResponse.setData("Successfully whitelisted");
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        }
        catch(SmsException e)
        {
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);

        }

    }
}
