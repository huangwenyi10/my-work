package com.example.praise.project.dao;

import com.example.praise.project.dto.MoodPraiseRelDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.example.praise.project.model.MoodPraiseRel;

@Mapper
public interface MoodPraiseRelDao {
    int insert(@Param("pojo") MoodPraiseRel pojo);

    int insertSelective(@Param("pojo") MoodPraiseRel pojo);

    int insertList(@Param("pojos") List<MoodPraiseRel> pojo);

    int update(@Param("pojo") MoodPraiseRel pojo);

    List<MoodPraiseRelDTO> findByMoodIdAndUserId(@Param("pojo") MoodPraiseRel pojo);

    int findCountByMoodIdAndUserId(@Param("pojo") MoodPraiseRel pojo);

    int findCountByMoodId(@Param("pojo") MoodPraiseRel pojo);
}
