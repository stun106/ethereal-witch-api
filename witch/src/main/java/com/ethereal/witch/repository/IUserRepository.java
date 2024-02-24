package com.ethereal.witch.repository;

import com.ethereal.witch.models.user.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<UserClient, Long> {
    UserClient findByUsername(String username);
    UserClient findByid(Long id);
    @Query("SELECT u FROM UserClient u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :user ,'%'))")
    List<UserClient> findUserlike(@Param("user") String user);
}
