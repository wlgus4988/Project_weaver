package com.example.test.user.jpa.repository;

import com.example.test.user.jpa.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userRepository extends JpaRepository<Users,String> {

    @Query(value = "select USERNAME from USERS", nativeQuery = true)
    List<String> findUsername();

    Page<Users> findByUsernameContainingOrNameContainingOrderByJoinDateDesc(String searchText, String searchText2, Pageable pageable);

}
