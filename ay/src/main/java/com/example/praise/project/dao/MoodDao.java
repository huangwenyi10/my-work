package com.example.praise.project.dao;

import com.example.praise.project.dto.MoodDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

import com.example.praise.project.model.Mood;

@Mapper
public interface MoodDao {
    int insert(@Param("pojo") Mood pojo);

    int insertSelective(@Param("pojo") Mood pojo);

    int insertList(@Param("pojos") List<Mood> pojo);

    int insertSet(@Param("pojos") Set<Mood> pojo);

    int update(@Param("pojo") Mood pojo);

    int batchUpdate(@Param("pojo") Set<Mood> set);

    MoodDTO getMoodById(@Param("id")String id);
}
