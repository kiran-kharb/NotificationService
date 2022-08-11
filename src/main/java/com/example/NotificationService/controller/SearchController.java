package com.example.NotificationService.controller;


import com.example.NotificationService.Entities.SearchEntity;
import com.example.NotificationService.Exceptions.SmsException;
import com.example.NotificationService.Request.SearchRequest;
import com.example.NotificationService.Response.ErrorResponse;
import com.example.NotificationService.Response.GenericResponse;
import com.example.NotificationService.service.SearchService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("v1/sms/search")
public class SearchController {

    @Autowired
    private final SearchService searchService ;

    @GetMapping
    List<SearchEntity> findAll()
    {
        System.out.println("controller");
        return searchService.findAll();
    }

    @GetMapping("/findByText/{text}")
    ResponseEntity<GenericResponse> findByText(@PathVariable("text") String text)
    {
        GenericResponse<Page<SearchEntity> , ErrorResponse> genericResponse=new GenericResponse<>();
        try{
                Page<SearchEntity> searchEntityPage=searchService.findByMessage(text);
                genericResponse.setData(searchEntityPage);
                return new ResponseEntity<>(genericResponse,HttpStatus.OK);
        }
        catch(SmsException e)
        {
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setCode("request not found");
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/findByPhoneInRange")
    ResponseEntity<GenericResponse> findInRange(@RequestBody SearchRequest searchRequest)
    {
        GenericResponse<Page<SearchEntity> , ErrorResponse> genericResponse=new GenericResponse<>();
        try{
            Page<SearchEntity> searchEntityPage=  searchService.findInRange(searchRequest);
            genericResponse.setData(searchEntityPage);
            return new ResponseEntity<>(genericResponse,HttpStatus.OK);
        }
        catch(SmsException e)
        {
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setCode("request not found");
            errorResponse.setMessage(e.getMessage());
            genericResponse.setError(errorResponse);
            return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
        }

    }
}
