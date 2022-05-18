package com.example.test.board.mybatis.service;

import com.example.test.board.mybatis.dto.FileDto;
import com.example.test.board.mybatis.mapper.fileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class fileServiceImpl implements fileService {

    @Autowired
    fileMapper filemapper;

    @Override
    public List<FileDto> findAllByBoardIdx(long boardIdx) throws  Exception {

        return filemapper.findAllByBoardIdx(boardIdx);
    }

    @Override
    public void deleteById(long fileIdx)throws  Exception {
        filemapper.deleteById(fileIdx);

    }

    @Override
    public long seq()  throws  Exception{
        return filemapper.seq();
    }

    @Override
    public FileDto findByIdx(long idx) throws  Exception{
        return filemapper.findByIdx(idx);
    }

    @Override
    public void fileSave(FileDto files) throws Exception {
        filemapper.fileSave(files);
    }




}
