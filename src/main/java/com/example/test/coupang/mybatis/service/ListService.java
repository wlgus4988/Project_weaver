package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import com.example.test.coupang.mybatis.dto.ListDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListService {

    void productSave(ListDto list, CoupangFileDto files, MultipartFile singleFile, List<MultipartFile> multiFiles) throws Exception;

    List<ListDto> findAll(@Param("searchText") String searchText, @Param("idx") long idx, Long proCateNum) throws Exception;

    ListDto findById(long productIdx) throws Exception;

    void deleteById(long productIdx) throws Exception;

    long seq() throws Exception;

}

