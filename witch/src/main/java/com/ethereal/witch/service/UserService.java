package com.ethereal.witch.service;

import com.ethereal.witch.models.user.User;
import com.ethereal.witch.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;
    @Transactional
    public User saveUser (User user){
        return iUserRepository.save(user);
    }
    @Transactional(readOnly = true)
    public User findUsername (String name){
        return iUserRepository.findByUsername(name);
    }
    @Transactional(readOnly = true)
    public User findById(Long id){
        return iUserRepository.findByid(id);
    }
    @Transactional(readOnly = true)
    public List<User> findAllUser(){
        return iUserRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<User> findiLike(String name){
        return iUserRepository.findUserIlike(name);
    }
    @Transactional
    public User ChangePassword(Long id, String currentPassword, String newPassword, String confirmPassword){
        if (!(newPassword.equals(confirmPassword))){
            throw new RuntimeException("New password does not match password confirmation.");
        }
        User user = iUserRepository.findByid(id);
        if (!(user.getPassword().equals(currentPassword))){
            System.out.println(currentPassword + " == " + user.getPassword());
            throw new RuntimeException("Your password does not match.");
        }
        user.setPassword(newPassword);
        this.saveUser(user);
        return user;
    }
    @Transactional
    public void deleteUser(Long id){
        iUserRepository.delete(this.findById(id));
    }
}