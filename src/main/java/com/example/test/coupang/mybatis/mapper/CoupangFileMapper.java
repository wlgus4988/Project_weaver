package com.example.test.coupang.mybatis.mapper;

import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoupangFileMapper {

    long seq();

    List<CoupangFileDto> findByIdx(long productIdx);

    void deleteById(long idx);

    void saveFile(CoupangFileDto files);
}
