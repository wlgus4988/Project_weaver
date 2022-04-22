package com.example.test.board.service;

import com.example.test.board.common.FileUtils;
import com.example.test.board.entity.Board;
import com.example.test.board.entity.BoardFileDto;
import com.example.test.board.entity.Files;
import com.example.test.board.repository.boardRepository;
import com.example.test.board.repository.fileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class boardServiceImpl implements boardService {

    @Autowired
    boardRepository boardrepository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    fileRepository filerepository;

    @Override
    public void boardSave(Board board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, Files files) throws Exception {

        List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest); // 받은 리스트

        System.out.println("-------------------------------------------------->파일개수"+list.size());
        if (CollectionUtils.isEmpty(list) == false) {
            board.setFilesCount(board.getFilesCount()+list.size());
            for (int i = 0; i < list.size(); i++) {
                files.setIdx(filerepository.seq());
                files.setBoardIdx(boardrepository.save(board).getBoardIdx());
                files.setCreatorId(board.getUsername());

                files.setFileSize(list.get(i).getFileSize());
                files.setOriginalFileName(list.get(i).getOriginalFileName());
                files.setStoredFilePath(list.get(i).getStoredFilePath());

                System.out.println("================>>>>>"+files.toString());
                filerepository.save(files);
            }
        } else {
            board.setFilesCount(board.getFilesCount());
            System.out.println("------파일 0개"+board.getFilesCount());
        }
        board.setHitCnt(hitCnt);
        boardrepository.save(board);
    }

    @Override
    public void boardDelete(long boardIdx, String NL) throws Exception {
        boardrepository.boardDelete(boardIdx, NL);
    }

    @Override
    public Board boardDetail(long boardIdx) throws Exception {

        Optional<Board> optional = boardrepository.findById(boardIdx);     // null 처리를 따로 안해도 된다는 장점이 있다는데 이해가 안된다 공부 필요

        if (optional.isPresent()) {
            Board board = optional.get();

            board.setHitCnt(board.getHitCnt() + 1);

            boardrepository.save(board);

            return board;
        }
        return null;
    }

}
