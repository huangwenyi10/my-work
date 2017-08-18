package com.example.intellij.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AyTestDao {

    int insert(@Param("pojo") AyTest pojo);

    int insertSelective(@Param("pojo") AyTest pojo);

    int insertList(@Param("pojos") List<AyTest> pojo);

    int update(@Param("pojo") AyTest pojo);
}
