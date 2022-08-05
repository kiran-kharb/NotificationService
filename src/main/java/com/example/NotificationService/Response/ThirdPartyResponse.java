package com.example.NotificationService.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyResponse {
    private String code;
    private String transid;
    private String description;
    private String correlationid;


}
