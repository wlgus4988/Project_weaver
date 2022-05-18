package com.example.test.coupang.mybatis.service;

import com.example.test.coupang.mybatis.dto.OptionDto;
import com.example.test.coupang.mybatis.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService{

    @Autowired
    OptionMapper optionmapper;

    @Override
    public List<OptionDto> findByProductIdx(long idx) {
        return optionmapper.findByProductIdx(idx);
    }

    @Override
    public void stockCount(long productIdx, String optionColor, String optionSize) {
        optionmapper.stockCount(productIdx,optionColor,optionSize);
    }

    @Override
    public long stock(long productIdx, String optionColor, String optionSize) {
        return optionmapper.stock(productIdx,optionColor,optionSize);
    }

    @Override
    public void optionSave(OptionDto option) {
        optionmapper.optionSave(option);
    }

    @Override
    public void deleteById(long optionIdx) {
        optionmapper.deleteById(optionIdx);

    }
}
