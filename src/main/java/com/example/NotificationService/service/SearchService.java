package com.example.NotificationService.service;

import com.example.NotificationService.Entities.SearchEntity;
import com.example.NotificationService.Repository.SearchRepository;
import com.example.NotificationService.Request.SearchRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Data
public class SearchService {

    @Autowired
    private final SearchRepository searchRepository;
    public List<SearchEntity> findAll()
    {
        System.out.println("check 2");
        Page<SearchEntity> searchEntityPage= searchRepository.findAll(PageRequest.of(0,100));
        return searchEntityPage.getContent();
    }

    public Page<SearchEntity> findByMessage(String text)
    {
        return searchRepository.findByMessage(text, PageRequest.of(0,100));
    }
    public Page<SearchEntity> findInRange( SearchRequest searchRequest)
    {
        String phoneNumber=searchRequest.getPhoneNumber();
        String start = searchRequest.getStartTime();
        String end= searchRequest.getEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        return searchRepository.findByPhoneNumberAndCrAtBetween(phoneNumber,startTime,endTime, PageRequest.of(0,100));
    }
}
