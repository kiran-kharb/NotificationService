package com.example.NotificationService.Configuration;

import com.example.NotificationService.Entities.SmsEntity;
import com.example.NotificationService.Repository.SmsRepository;
import org.elasticsearch.core.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    @Bean
    CommandLineRunner commandLineRunner(SmsRepository repository)
    {
        return args -> {
            SmsEntity sms1 =new SmsEntity("+916283558105" ,"Hello World");
            SmsEntity sms2 =new SmsEntity("6239458574" , "watcha doin");
            repository.saveAll(
                    List.of(sms1,sms2)
            );
        };


    }

}
