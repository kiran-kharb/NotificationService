package com.example.NotificationService.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash("BlacklistEntity")
public class BlacklistEntity implements Serializable {

    @Id
    private String phone_number;
    private boolean ifBlacklisted;

}
