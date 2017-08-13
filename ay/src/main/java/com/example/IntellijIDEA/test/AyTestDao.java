package com.example.IntellijIDEA.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.example.IntellijIDEA.test.AyTest;

@Mapper
public interface AyTestDao {

    int insert(@Param("pojo") AyTest pojo);

    int insertSelective(@Param("pojo") AyTest pojo);

    int insertList(@Param("pojos") List<AyTest> pojo);

    int update(@Param("pojo") AyTest pojo);
}
