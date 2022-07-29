package com.example.NotificationService.Response;

import lombok.Data;

@Data
public class PostReqResponse {
    private String pno;
    private String msg;
    @lombok.Data
    public static class Data{
        private int req_id;
        private String comments;
    }
    @lombok.Data // static is important here
    public static class Error{
        private String code;
        private String msg;
    }
}
