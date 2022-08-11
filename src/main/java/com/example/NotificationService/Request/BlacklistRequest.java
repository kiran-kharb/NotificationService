package com.example.NotificationService.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistRequest {

    @JsonProperty("phone_numbers")
    private List<String>  phone_numbers;

}
