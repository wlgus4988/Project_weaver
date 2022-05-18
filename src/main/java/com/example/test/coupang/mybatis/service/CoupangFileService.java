package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.mybatis.dto.CoupangFileDto;

import java.util.List;

public interface CoupangFileService {

    long seq();

    List<CoupangFileDto> findByIdx(long idx);

    void deleteById(long idx);

}
