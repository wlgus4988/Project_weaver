package com.example.test.coupang.jpa.repository;

import com.example.test.coupang.jpa.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoupangFileRepository extends JpaRepository<FileEntity, Long> {

    @Query(value ="select FILE_SEQ.NEXTVAL from dual", nativeQuery =true)
    long seq();

    @Query(value = "select * from COUPANG_FILES where PRODUCT_IDX = :Idx", nativeQuery = true)
    List<FileEntity> findByIdx(long Idx);


}
