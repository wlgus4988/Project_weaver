package com.example.test.board.mybatis.mapper;


import com.example.test.board.mybatis.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface fileMapper {

    List<FileDto> findAllByBoardIdx(long boardIdx);

    void deleteById(long fileIdx);

    long seq();

    FileDto findByIdx(long idx);

    void fileSave(FileDto files);

}
