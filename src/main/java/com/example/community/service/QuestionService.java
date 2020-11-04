package com.example.community.service;

import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionDTOMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDTOMapper questionDTOMapper;

    @Autowired
    private UserMapper userMapper;

//传统方式分页
   /* public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        //判断页码是否越界
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        //分页查询
        Integer offSet = size * (page - 1);
        List<Question> questionList = questionMapper.list(offSet,size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //遍历questionList，将user信息存入dto后，再加入集合
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        //将集合存入分页类，返回
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }*/


    public List<QuestionDTO> list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<QuestionDTO> questionDTOList = questionDTOMapper.findAll();
        return questionDTOList;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount =(int) questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);

        //判断页码是否越界
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        //分页查询
        Integer offSet = size * (page - 1);
        if (offSet<0){
            offSet=0;
        }
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example1,new RowBounds(offSet,size));


        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //遍历questionList，将user信息存入dto后，再加入集合
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        //将集合存入分页类，返回
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        //问题没有则创建
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //有就更新
//            Question updateQuestion = new Question();
//            updateQuestion.setGmtModified(System.currentTimeMillis());
//            updateQuestion.setTitle(question.getTitle());
//            updateQuestion.setDescription(question.getDescription());
//            updateQuestion.setTag(question.getTag());
//            QuestionExample questionExample = new QuestionExample();
//            questionExample.createCriteria()
//                    .andIdEqualTo(question.getId());
//            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateByPrimaryKeySelective(question);
        }
    }
}
