package com.example.NotificationService.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyResponse {
    @JsonProperty("code")
    private String code;
    @JsonProperty("transid")
    private String transid;
    @JsonProperty("description")
    private String description;
    @JsonProperty("correlationid")
    private String correlationid;


}
