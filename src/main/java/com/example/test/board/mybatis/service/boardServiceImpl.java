package com.example.test.board.mybatis.service;

import com.example.test.board.common.FileUtils;
import com.example.test.board.jpa.entity.Board;
import com.example.test.board.jpa.entity.BoardFileDto;
import com.example.test.board.jpa.entity.Files;
import com.example.test.board.jpa.repository.boardRepository;
import com.example.test.board.jpa.repository.fileRepository;
import com.example.test.board.mybatis.dto.BoardDto;
import com.example.test.board.mybatis.dto.FileDto;
import com.example.test.board.mybatis.mapper.boardMapper;
import com.example.test.board.mybatis.mapper.fileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
public class boardServiceImpl implements boardService {

   /* @Autowired
    boardRepository boardrepository;*/

    @Autowired
    private FileUtils fileUtils;

    /*@Autowired
    fileRepository filerepository;*/

    @Autowired
    fileMapper filemapper;

    @Autowired
    boardMapper boardmapper;

    @Override
    public BoardDto findByBoardIdx(long pBoardIdx) throws Exception {
        //boardmapper.findByBoardIdx(pBoardIdx);
        System.out.println("----------------------------->serviceImpl");
        System.out.println(boardmapper.findByBoardIdx(pBoardIdx));
        return boardmapper.findByBoardIdx(pBoardIdx);
    }

    @Override
    public List<BoardDto> findAll(String searchText, String searchText2, String boardName) {

        return boardmapper.findAll(searchText, searchText, boardName);
    }

    @Override
    public void boardSave(BoardDto board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, FileDto files) throws Exception {
        long boardIdx = boardmapper.Seq();
        List<BoardFileDto> list = fileUtils.parseFileInfo(boardIdx, multipartHttpServletRequest); // 받은 리스트
        System.out.println("-------------------------------------------------->파일개수" + list.size());
        if (CollectionUtils.isEmpty(list) == false) {
            board.setFilesCount(board.getFilesCount() + list.size());
            for (int i = 0; i < list.size(); i++) {
                files.setIdx(filemapper.seq());
                files.setBoardIdx(boardIdx);
                files.setCreatorId(board.getUsername());

                files.setFileSize(list.get(i).getFileSize());
                files.setOriginalFileName(list.get(i).getOriginalFileName());
                files.setStoredFilePath(list.get(i).getStoredFilePath());

                System.out.println("================>>>>>" + files.toString());
                filemapper.fileSave(files);
            }
        } else {
            board.setFilesCount(board.getFilesCount());
            System.out.println("------파일 0개" + board.getFilesCount());
        }
        board.setHitCnt(hitCnt);
        board.setBoardIdx(boardIdx);
        boardmapper.boardSave(board);
    }

    @Override
    public void boardUpdate(BoardDto board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, FileDto files) throws Exception {

        List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest); // 받은 리스트

        System.out.println("-------------------------------------------------->파일개수" + list.size());
        if (CollectionUtils.isEmpty(list) == false) {
            board.setFilesCount(board.getFilesCount() + list.size());
            for (int i = 0; i < list.size(); i++) {
                files.setIdx(filemapper.seq());
                files.setBoardIdx(board.getBoardIdx());
                files.setCreatorId(board.getUsername());

                files.setFileSize(list.get(i).getFileSize());
                files.setOriginalFileName(list.get(i).getOriginalFileName());
                files.setStoredFilePath(list.get(i).getStoredFilePath());

                System.out.println("================>>>>>" + files.toString());
                filemapper.fileSave(files);
            }
        } else {
            board.setFilesCount(board.getFilesCount());
            System.out.println("------파일 0개" + board.getFilesCount());
        }
        board.setHitCnt(hitCnt);
        boardmapper.boardUpdate(board);
    }

    @Override
    public void boardDelete(long boardIdx, String NL) throws Exception {
        boardmapper.boardDelete(boardIdx, NL);
    }

    @Override
    public void deleteFile(long boardIdx) {
        boardmapper.deleteFile(boardIdx);
    }


    @Override
    public BoardDto boardDetail(long boardIdx) throws Exception {

        BoardDto board = boardmapper.findByBoardIdx(boardIdx);     // null 처리를 따로 안해도 된다는 장점이 있다는데 이해가 안된다 공부 필요

        //board.setHitCnt(board.getHitCnt() + 1);

        boardmapper.boardSetHitCnt(boardIdx);

        return board;

    }

    @Override
    public List<Long> selectGroupNum() {

        return boardmapper.selectGroupNum();
    }

    @Override
    public List<Long> findGroupIdx(long pBoardIdx) throws Exception {

        return boardmapper.findGroupIdx(pBoardIdx);
    }

}
