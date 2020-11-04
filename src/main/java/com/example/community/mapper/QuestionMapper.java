package com.example.community.mapper;

import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import javax.websocket.OnError;
import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offSet, @Param("size") Integer size);

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUser(@Param("userId") Integer userId,@Param("offset") Integer offSet, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserID(Integer userId);

    @Select("select * from question")
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

    @Update("update question set title = #{title},description=#{description},tag = #{tag},gmt_modified=#{gmtModified} where id = #{id}")
    void update(Question question);

}
