package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.mybatis.dto.CateDto;
import com.example.test.coupang.mybatis.mapper.CateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    @Autowired
    CateMapper cateMapper;

    @Override
    public List<CateDto> findAll(String searchText) throws Exception {
        return cateMapper.findAll(searchText);
    }

    @Override
    public List<Long> findGroupNum() throws Exception {
        return cateMapper.findGroupNum();
    }

    @Override
    public List<Long> findGroupIdx(long pIdx) throws Exception {
        return cateMapper.findGroupIdx(pIdx);
    }

    @Override
    public List<CateDto> findByPIdx(long pIdx) throws Exception {
        return cateMapper.findByPIdx(pIdx);
    }

    @Override
    public void cateUpdate(String cateUpdateName, String cateName) throws Exception {
        cateMapper.cateUpdate(cateUpdateName, cateName);
    }

    @Override
    public void cateDelete(long idx) throws Exception {
        cateMapper.cateDelete(idx);
    }

    @Override
    public List<CateDto> findByDepth(long depth) throws Exception {
        return cateMapper.findByDepth(depth);
    }

    @Override
    public String pCateName(long groupNum, long idx) throws Exception {
        return cateMapper.pCateName(groupNum, idx);
    }

    @Override
    public String CateName(long idx) throws Exception {
        return cateMapper.CateName(idx);
    }

    @Override
    public CateDto findById(long idx) throws Exception {
        return cateMapper.findById(idx);
    }

    @Override
    public void cateSave(CateDto cate) throws Exception {
        cateMapper.cateSave(cate);
    }
}
