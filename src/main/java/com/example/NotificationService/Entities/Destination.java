package com.example.NotificationService.Entities;

import lombok.Data;

import java.util.List;

@Data
public class Destination {
    List<String> msisdn;
   String correlationId;
}
