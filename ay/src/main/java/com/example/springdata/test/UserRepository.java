package com.example.springdata.test;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author  Ay
 * @date    2017/08/22
 */
public interface UserRepository extends CrudRepository<AyTest, String> {


    List<AyTest> findByIdAndName(String id, String name);

}


