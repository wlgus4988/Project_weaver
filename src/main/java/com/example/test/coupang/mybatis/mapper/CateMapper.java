package com.example.test.coupang.mybatis.mapper;

import com.example.test.coupang.mybatis.dto.CateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CateMapper {

    List<CateDto> findAll(String searchText) throws Exception;

    List<Long> findGroupNum() throws Exception;

    List<Long> findGroupIdx(long pIdx) throws Exception;

    List<CateDto> findByPIdx(long pIdx) throws Exception;

    void cateUpdate(@Param("cateUpdateName") String cateUpdateName, @Param("cateName") String cateName) throws Exception;

    void cateDelete(long idx) throws Exception;

    List<CateDto> findByDepth(long depth) throws Exception;

    String pCateName(long groupNum, long idx) throws Exception;

    String CateName(long idx) throws Exception;

    CateDto findById(long idx) throws Exception;

    void cateSave(CateDto cate) throws Exception;
}
