package com.example.cache.test;

import com.example.springdata.test.AyTest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author  Ay
 * @date    2017/08/22
 */
@CacheConfig(cacheNames = "users")
public interface UserCacheRepository extends CrudRepository<AyTest, String> {

    @Cacheable
    AyTest findById(String id);

}


