package com.example.NotificationService.Repository;

import com.example.NotificationService.Entities.BlacklistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlacklistRepository {
    @Autowired
    private RedisTemplate template;
    public static final String HASH_KEY="BlacklistEntity";
    public BlacklistEntity save(BlacklistEntity blacklistEntity)
    {
         template.opsForHash().put(HASH_KEY ,blacklistEntity.getPhone_number() ,blacklistEntity);
         return blacklistEntity;
    }
    public List<BlacklistEntity> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public BlacklistEntity findByID(int id)
    {
        return (BlacklistEntity) template.opsForHash().get(HASH_KEY,id);
    }
    public String DeleteById(int id)
    {
        template.opsForHash().delete(HASH_KEY,id);
        return "Successfully Whitelisted";
    }


}
