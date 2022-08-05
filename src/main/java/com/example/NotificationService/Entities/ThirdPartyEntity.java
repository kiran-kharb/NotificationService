package com.example.NotificationService.Entities;

import lombok.Data;

import java.util.List;

@Data
public class ThirdPartyEntity
{
    private String deliveryChannel="sms";
    private List<Destination> destination;
    private Channels channels;

    @lombok.Data
    public static class Channels
    {

        @lombok.Data
        public static class Sms
        {
            String text;
        }
    }

    @Data
    public static class Destination{
        List<String> msidn;
        String correlationId;

    }


}
