package com.example.test.coupang.jpa.service;

import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListService {

    void productSave(ListEntity list, FileEntity files, MultipartFile singleFile, List<MultipartFile> multiFiles) throws Exception;
}

