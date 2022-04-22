package com.example.test.board.entity;

import lombok.Data;

@Data
public class BoardFileDto {
    private long idx;
    private long boardIdx;
    private String originalFileName;
    private String originalFilePath;
    private String storedFilePath;
    private long fileSize;
}
