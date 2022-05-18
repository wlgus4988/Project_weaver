package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.mybatis.dto.OptionDto;

import java.util.List;

public interface OptionService {

    List<OptionDto> findByProductIdx(long idx);
    void stockCount(long productIdx, String optionColor, String optionSize);
    long stock(long productIdx, String optionColor, String optionSize);
    void optionSave(OptionDto option);
    void deleteById(long optionIdx);

}
