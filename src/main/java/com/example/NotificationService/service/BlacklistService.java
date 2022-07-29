package com.example.NotificationService.service;

import com.example.NotificationService.Entities.BlacklistEntity;
import com.example.NotificationService.Repository.BlacklistRepository;
import com.example.NotificationService.Request.BlacklistRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class BlacklistService {

    @Autowired
    private final BlacklistRepository blacklistRepository;

    public List<BlacklistEntity> GetBlacklistNumbers()
    {
        return blacklistRepository.findAll();

    }

    public boolean BlacklistNumbers(BlacklistRequest blacklistRequest) //list of
    {
        try{
            List<String> phone_numbers=blacklistRequest.getPhone_numbers();
            for(String number : phone_numbers)
            {
                BlacklistEntity b=new BlacklistEntity(number,true);
                blacklistRepository.save(b);
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }


        //return "true";
    }
}