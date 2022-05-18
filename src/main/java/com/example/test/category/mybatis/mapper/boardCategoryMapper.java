package com.example.test.category.mybatis.mapper;

import com.example.test.category.mybatis.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface boardCategoryMapper {

    List<CategoryDto> categoryList(String searchText) throws Exception;

    long totalBoardCount() throws Exception;

    void boardListSave(CategoryDto cate) throws Exception;

    CategoryDto findById(long idx) throws Exception;

    void boardNameUpdate(@Param("boardUpdateName") String boardUpdateName, @Param("boardName") String boardName) throws Exception;

    void deleteById(long idx) throws Exception;

    void boardCountUpdate(String boardName);

    List<CategoryDto> findAll() throws Exception;
}
