package com.example.test.coupang.jpa.repository;

import com.example.test.coupang.jpa.entity.CateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CateRepository extends JpaRepository<CateEntity,Long> {

    Page<CateEntity> findByCateNameContainingOrderByIdxDesc(String searchText, Pageable pageable);

    @Query(value = "select GROUP_NUM from COUPANG_CATEGORY where P_IDX = 0", nativeQuery = true)
    List<Long> findGroupNum();

    @Query(value = "select GROUP_IDX from COUPANG_CATEGORY where P_IDX = :pIdx", nativeQuery = true)
    List<Long> findGroupIdx(long pIdx);

    @Query(value = "select *  from COUPANG_CATEGORY where P_IDX = :pIdx AND DELETEYN='N'", nativeQuery = true)
    List<CateEntity> findByPIdx(long pIdx);

   /* @Query(value = "select idx from COUPANG_CATEGORY where CATE_NAME LIKE %:cateName%", nativeQuery = true)
    List<Long> findByCateName(String cateName);*/

/*    @Transactional
    @Modifying
    @Query(value="UPDATE COUPANG_PRODUCT SET PRODUCT_CATEGORY = :cateUpdateName WHERE PRODUCT_CATEGORY = :cateName", nativeQuery = true)
    void productCateUpdate(String cateUpdateName, String cateName);*/

    @Transactional
    @Modifying
    @Query(value="UPDATE COUPANG_CATEGORY SET CATE_NAME = :cateUpdateName WHERE CATE_NAME = :cateName", nativeQuery = true)
    void cateNameUpdate(String cateUpdateName, String cateName);

    @Transactional
    @Modifying
    @Query(value="UPDATE COUPANG_CATEGORY SET deleteYN = 'Y' WHERE IDX = :idx", nativeQuery = true)
    void cateDelete(long idx);

    @Query(value= "SELECT * FROM COUPANG_CATEGORY where CATE_NAME LIKE %:searchText% AND DELETEYN='N' START WITH P_IDX = 0 CONNECT BY PRIOR IDX = P_IDX order siblings by GROUP_NUM desc", nativeQuery = true)
    List<CateEntity> findAll(@Param("searchText") String searchText);

    @Query(value= "SELECT * FROM COUPANG_CATEGORY where depth = 0 order by GROUP_NUM asc", nativeQuery = true)
    List<CateEntity> findByDepth();
    @Query(value= "SELECT * FROM COUPANG_CATEGORY where depth = 1 order by GROUP_NUM asc", nativeQuery = true)
    List<CateEntity> findByDepth1();
    @Query(value= "SELECT * FROM COUPANG_CATEGORY where depth = 2 order by GROUP_NUM asc", nativeQuery = true)
    List<CateEntity> findByDepth2();
    @Query(value= "SELECT * FROM COUPANG_CATEGORY where depth = 3 order by GROUP_NUM asc", nativeQuery = true)
    List<CateEntity> findByDepth3();

    @Query(value= "SELECT CATE_NAME FROM COUPANG_CATEGORY where GROUP_NUM = :groupNum AND IDX = :idx", nativeQuery = true)
    String pCateName(long groupNum, long idx);

    @Query(value= "SELECT CATE_NAME FROM COUPANG_CATEGORY where IDX = :idx", nativeQuery = true)
    String CateName(long idx);


}
