package com.example.NotificationService.Entities;

import lombok.Data;

import java.util.List;

@Data
public class ThirdPartyEntity
{
    private String deliverychannel="sms";

    private Channels channels;
    private List<Destination> destination;


}
