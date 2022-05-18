package com.example.test.board.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data // get, set
public class FileDto {
    private long idx;
    private long boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;
    private Date createdDatetime;

}
