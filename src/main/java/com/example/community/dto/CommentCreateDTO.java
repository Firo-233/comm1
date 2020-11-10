package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
