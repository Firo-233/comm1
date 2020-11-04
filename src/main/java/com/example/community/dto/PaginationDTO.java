package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;

    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;//当前页码
    //页码集合 例如(1,2,3,4,5) (2,3,4,5,6)
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    /**
     *
     * @param totalCount    总记录数
     * @param page          当前页码
     * @param size          每页展示的记录数量
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }


        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if (page + i <= totalPage) {               //3
                pages.add(page + i);
            }
        }

        //如果为第一页，则不显示前进图标
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //如果为末页，则不显示后退图标
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示前进到第一页图标
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage =true;
        }

        //是否展示后退到最后一页图标
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage =true;
        }


    }
}
