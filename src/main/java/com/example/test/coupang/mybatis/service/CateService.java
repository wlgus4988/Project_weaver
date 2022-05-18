package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.mybatis.dto.CateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CateService {

    List<CateDto> findAll(String searchText) throws Exception;

    List<Long> findGroupNum() throws Exception;

    List<Long> findGroupIdx(long pIdx) throws Exception;

    List<CateDto> findByPIdx(long pIdx) throws Exception;

    void cateUpdate(String cateUpdateName, String cateName) throws Exception;

    void cateDelete(long idx) throws Exception;

    List<CateDto> findByDepth(long depth) throws Exception;

    String pCateName(long groupNum, long idx) throws Exception;

    String CateName(long idx) throws Exception;

    CateDto findById(long idx) throws Exception;

    void cateSave(CateDto cate) throws Exception;

}
