package com.example.test.coupang.mybatis.mapper;

import com.example.test.coupang.mybatis.dto.OptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OptionMapper {
    List<OptionDto> findByProductIdx(long productIdx);
    void stockCount(@Param("productIdx") long productIdx, @Param("optionColor") String optionColor,@Param("optionSize") String optionSize);
    long stock(@Param("productIdx") long productIdx, @Param("optionColor") String optionColor,@Param("optionSize") String optionSize);

    void optionSave(OptionDto option);
    void deleteById(long optionIdx);

    long seq();

}
