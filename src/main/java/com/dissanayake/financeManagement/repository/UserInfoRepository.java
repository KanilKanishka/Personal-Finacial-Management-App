package com.dissanayake.financeManagement.repository;


import com.dissanayake.financeManagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByEmailId(String emailId);
}
