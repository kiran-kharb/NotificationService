package com.example.NotificationService.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse <T,U>{

    @JsonProperty("data")
    T data;
    @JsonProperty("error")
    U error;
}
