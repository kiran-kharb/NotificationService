package com.example.NotificationService.Entities;
import com.example.NotificationService.Request.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
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
     private int id;
     private String pno ;//condition for checking validity
     private String msg;
     private String status; //save : In_progress , after kafka consumer : processing ,
     private long failure_code; //after sending to 3rd party
     private String failure_comment;
     private LocalDateTime created_at; //
     private LocalDateTime updated_at;


     public SmsEntity(SmsRequest sms)
     {
          this.pno=sms.getPno();
          this.msg=sms.getMsg();

     }
     public SmsEntity (String pno , String msg)
     {
          this.pno=pno;
          this.msg=msg;
     }

     @Override
     public String toString() {
          return "SmsEntity{" +
                  "id=" + id +
                  ", pno=" + pno +
                  ", msg='" + msg + '\'' +
                  '}';
     }
}
