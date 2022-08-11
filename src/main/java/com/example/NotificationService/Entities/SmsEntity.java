package com.example.NotificationService.Entities;

import com.example.NotificationService.Request.SmsRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

//column name :
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsEntity {
     @Id
     @SequenceGenerator(
             name = "sms_sequence",
             sequenceName = "sms_sequence",
             allocationSize = 1
     )
     @GeneratedValue(
             strategy = GenerationType.SEQUENCE,
             generator = "sms_sequence"
     )
     @Column(name="id")
     private int id;

     @GeneratedValue(generator = "UUID")
     @GenericGenerator(
             name = "UUID",
             strategy = "org.hibernate.id.UUIDGenerator"
     )
     @Column(name = "request_id", unique=true,updatable = false, nullable = false)
     private String requestId;

     @PrePersist
     public void autofill() {
          this.requestId= UUID.randomUUID().toString();
     }

     @Column(name="phone_number")
     private String phoneNumber;//condition for checking validity
     @Column(name="message")
     private String message;
     @Column(name="status")
     private String status; //save : In_progress , after kafka consumer : processing ,
     @Column(name="failure_code")
     private long failureCode; //after sending to 3rd party
     @Column(name="failure_comments")
     private String failureComment;
     @Column(name="created_at")
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     private LocalDateTime createdAt; //
     @Column(name="updated_at")
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     private LocalDateTime updatedAt;


     public SmsEntity(SmsRequest sms)
     {
          this.phoneNumber =sms.getPno();
          this.message =sms.getMsg();

     }
     public SmsEntity (String phoneNumber, String message)
     {
          this.phoneNumber = phoneNumber;
          this.message = message;
     }

     @Override
     public String toString() {
          return "SmsEntity{" +
                  "id=" + id +
                  ", pno=" + phoneNumber +
                  ", msg='" + message + '\'' +
                  '}';
     }
}
