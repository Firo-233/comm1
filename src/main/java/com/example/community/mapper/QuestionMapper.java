package com.example.community.mapper;

import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import javax.websocket.OnError;
import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offSet, @Param("size") Integer size);

    @Select("select * from question")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "tag",property = "tag"),
            @Result(column = "gmtCreate",property = "gmtCreate"),
            @Result(column = "gmtModified",property = "gmtModified"),
            @Result(column = "creator",property = "creator"),
            @Result(column = "viewCount",property = "viewCount"),
            @Result(column = "commentCount",property = "commentCount"),
            @Result(column = "likeCount",property = "likeCount"),
            @Result(column = "creator",property = "user",one = @One(select = "com.example.community.mapper.UserMapper.findById"))
    })
    List<QuestionDTO> findAll();

    @Select("select count(1) from question")
    Integer count();
}
