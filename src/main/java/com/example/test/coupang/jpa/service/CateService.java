package com.example.test.coupang.jpa.service;

import com.example.test.coupang.jpa.entity.CateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CateService {

    Page<CateEntity> catePageList(String searchText, Pageable pageable) throws Exception;
}
