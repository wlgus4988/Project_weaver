package com.example.test.coupang.mybatis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data // get, set
public class CoupangFileDto {

    private long idx;

    private long productIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;

    private String mainImage = "N";

    private Date createDatetime;

}
