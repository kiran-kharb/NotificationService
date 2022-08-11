package com.example.NotificationService.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListThirdPartyResponse {
    @JsonProperty("response")
    private List<ThirdPartyResponse> thirdPartyResponseList;
}
