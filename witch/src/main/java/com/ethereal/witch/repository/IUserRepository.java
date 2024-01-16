package com.ethereal.witch.repository;

import com.ethereal.witch.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByid(Long id);
    @Query("SELECT u FROM User u WHERE LOWER(u.name) ILIKE LOWER(CONCAT('%', :user ,'%'))")
    List<User> findUserIlike(@Param("user") String user);
}
