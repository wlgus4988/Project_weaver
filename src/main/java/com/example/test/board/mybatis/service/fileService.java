package com.example.test.board.mybatis.service;

import com.example.test.board.mybatis.dto.FileDto;

import java.util.List;

public interface fileService {

    List<FileDto> findAllByBoardIdx(long boardIdx) throws  Exception;

    void deleteById(long fileIdx) throws  Exception;

    long seq() throws  Exception;

    FileDto findByIdx(long idx) throws  Exception;

    void fileSave(FileDto files) throws  Exception;

}
