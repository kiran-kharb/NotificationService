package com.example.NotificationService.Repository;

import com.example.NotificationService.Entities.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsRepository extends JpaRepository<SmsEntity,Integer> {
    //boolean existsByRequestId(String requestId);
    boolean  existsSmsEntityByRequestId(String requestId);
    Optional<SmsEntity> findByRequestId(String requestId);
}
