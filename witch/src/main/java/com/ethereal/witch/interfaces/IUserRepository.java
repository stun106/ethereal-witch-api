package com.ethereal.witch.interfaces;

import com.ethereal.witch.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByid(Long id);

    @Override
    List<User> findAll();
}
