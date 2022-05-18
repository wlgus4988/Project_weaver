package com.example.test.coupang.mybatis.mapper;

import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import com.example.test.coupang.mybatis.dto.ListDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface ListMapper {

    void productSave(ListDto list) throws Exception;

    List<ListDto> findAll(@Param("searchText") String searchText, @Param("idx") long idx, Long proCateNum) throws Exception;

    ListDto findById(long productIdx) throws Exception;

    void deleteById(long productIdx) throws Exception;

    long seq() throws Exception;
}
