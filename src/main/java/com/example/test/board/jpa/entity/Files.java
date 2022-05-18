package com.example.test.board.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "FILES")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class Files {

    @Id
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;

    private long boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;

    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date createdDatetime;

}
