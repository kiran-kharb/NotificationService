package com.example.NotificationService.Repository;

import com.example.NotificationService.Entities.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<SmsEntity, Integer> {

}
