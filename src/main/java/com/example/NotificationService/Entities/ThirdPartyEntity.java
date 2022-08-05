package com.example.NotificationService.Entities;

import lombok.Data;

import java.util.List;

@Data
public class ThirdPartyEntity
{
    private String deliverychannel="sms";

    private Channels channels;
    private List<Destination> destination;

   // @Data
//    public static class Channels
//    {
//        private Sms sms;
//
//       @Data
//        public static class Sms
//        {
//            String text;
//        }
//    }
//
//    @Data
//    public static class Destination{
//        List<String> msisdn;
//        String correlationId;
//
//    }


}
