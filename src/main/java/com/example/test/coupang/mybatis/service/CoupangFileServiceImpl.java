package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import com.example.test.coupang.mybatis.mapper.CoupangFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoupangFileServiceImpl implements CoupangFileService{

    @Autowired
    CoupangFileMapper coupangfilemapper;

    @Override
    public long seq() {
        return coupangfilemapper.seq();
    }

    @Override
    public List<CoupangFileDto> findByIdx(long idx) {
        return coupangfilemapper.findByIdx(idx);
    }

    @Override
    public void deleteById(long idx) {
        coupangfilemapper.deleteById(idx);
    }

}
