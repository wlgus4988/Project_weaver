package com.example.test.coupang.jpa.repository;

import com.example.test.coupang.jpa.entity.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> {

    @Query(value= "SELECT * FROM COUPANG_PRODUCT where PRODUCT_NAME LIKE %:searchText% AND PRODUCT_CATEGORY_NUM = :idx order by PRODUCT_IDX desc", nativeQuery = true)
    List<ListEntity> findAll(@Param("searchText") String searchText, long idx);

    @Query(value= "SELECT * FROM COUPANG_PRODUCT where PRODUCT_NAME LIKE %:searchText% AND PRODUCT_CATEGORY_NUM = :idx AND PRODUCT_TYPE Like %:proCateNum% order by PRODUCT_IDX desc", nativeQuery = true)
    List<ListEntity> findAll2(@Param("searchText") String searchText, long idx, @Param("proCateNum") long proCateNum);



}
