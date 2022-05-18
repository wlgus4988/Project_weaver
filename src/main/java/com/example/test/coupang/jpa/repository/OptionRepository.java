package com.example.test.coupang.jpa.repository;

import com.example.test.coupang.jpa.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    List<OptionEntity> findByProductIdxOrderByOptionColorAscOptionSizeDesc(long idx);

    @Transactional
    @Modifying
    @Query(value="UPDATE COUPANG_PRODUCT_OPTION SET PRODUCT_STOCK = PRODUCT_STOCK - 1 WHERE PRODUCT_IDX = :productIdx AND OPTION_COLOR= :optionColor AND OPTION_SIZE = :optionSize", nativeQuery = true)
    void stockCount(long productIdx, String optionColor, String optionSize);

    @Query(value="Select PRODUCT_STOCK from COUPANG_PRODUCT_OPTION where  PRODUCT_IDX = :productIdx AND OPTION_COLOR= :optionColor AND OPTION_SIZE = :optionSize", nativeQuery = true)
    long stock(long productIdx, String optionColor, String optionSize);
}
