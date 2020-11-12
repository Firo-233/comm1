package com.example.community.mapper;

import com.example.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface QuestionDTOMapper {

    @Select("select * from question ORDER BY gmt_create desc")
    @Results(id ="QuestionDTO", value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "tag",property = "tag"),
            @Result(column = "gmtCreate",property = "gmt_create"),
            @Result(column = "gmtModified",property = "gmt_modified"),
            @Result(column = "creator",property = "creator"),
            @Result(column = "commentCount",property = "comment_count"),
            @Result(column = "viewCount",property = "view_count"),
            @Result(column = "likeCount",property = "like_count"),
            @Result(column = "creator",property = "user",one = @One(select = "com.example.community.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<QuestionDTO> findAll();

    @Select("select * from question where id = #{id}")
    @ResultMap("QuestionDTO")
    QuestionDTO getById(Integer id);

}
