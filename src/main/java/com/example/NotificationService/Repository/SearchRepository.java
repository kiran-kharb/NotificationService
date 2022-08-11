package com.example.NotificationService.Repository;

import com.example.NotificationService.Entities.SearchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchEntity, Integer> {

        Page<SearchEntity> findAll(Pageable pageable);
        Page<SearchEntity> findByMessage(String text, Pageable pageable);
        Page<SearchEntity> findByPhoneNumberAndCrAtBetween(String phoneNumber , LocalDateTime startTime , LocalDateTime endTime,Pageable pageable);
}
