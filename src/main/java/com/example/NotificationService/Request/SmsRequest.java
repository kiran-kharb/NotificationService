package com.example.NotificationService.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//#converts any input into JSon format , in Post request we pass the input as an instance of this class (now input
// can be in any format)
public class SmsRequest {
    @JsonProperty("pno")
  //  @Pattern(regexp="^[+]?[0-9]{0,2}?[0-9]{10}$",message="Invalid Phone Number!")
    private String pno;

    @JsonProperty("msg")
    private String msg;

}
