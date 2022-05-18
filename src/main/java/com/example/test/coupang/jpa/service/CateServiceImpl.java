package com.example.test.coupang.jpa.service;

import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//@Service
public class CateServiceImpl implements CateService{

    @Autowired
    CateRepository caterepo;

    @Override
    public Page<CateEntity> catePageList(String searchText, Pageable pageable) throws Exception {

        Page<CateEntity> list = caterepo.findByCateNameContainingOrderByIdxDesc(searchText, pageable);

        return list;
    }
}
