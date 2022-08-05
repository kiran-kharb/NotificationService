package com.example.NotificationService.controller;

import com.example.NotificationService.Entities.BlacklistEntity;
import com.example.NotificationService.Request.BlacklistRequest;
import com.example.NotificationService.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String BlacklistNumber(@RequestBody BlacklistRequest blacklistRequest)
    {
       boolean bool = blacklistService.BlacklistNumbers(blacklistRequest);
       if(bool)
        return "post successfull";

       return "unsuccessfull";
    }
    @DeleteMapping("/{phonenumber}")
    public String WhitelistNumbers(@PathVariable("phonenumber") String phonenumber)
    {
        boolean b=blacklistService.whitelist(phonenumber);
        if(b)
            return "successfully whitelisted";
        return "error";
    }
}
