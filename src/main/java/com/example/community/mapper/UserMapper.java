package com.example.community.mapper;

import com.example.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("Insert into user (name,account_Id,token,gmt_Create,gmt_Modified) values (#{name},#{account_Id},#{token},#{gmt_Create},#{gmt_Modified})")
    void insert(User user);

}
