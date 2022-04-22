package com.example.test.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity                // 데이블을 만드는 엔티티 객체 생성
@Table(name = "USERS") 	// 테이블명 다르게 줌('users' 로)
@NoArgsConstructor        // 디폴트 생성자
@AllArgsConstructor    // 전체 컬럼 생성자
@Data                    // get, set
@Builder
public class Users {

    // 테이블이 없다면 알아서 create 함
    @Id                        // primary 키 잡기 / @GeneratedValue 숫자용
    @Column(name = "USERNAME") 	// 디비버의 테이블 컬럼명을 바꿈
    private String username;
    private String pw;
    private String name;
    private String email;

    // date 추가
    @Temporal(TemporalType.DATE) // 데이터베이스에서 DATE 타입으로 주기
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date joinDate;
    private String role;

}
