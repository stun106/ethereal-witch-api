package com.ethereal.witch.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.models.user.AccessUser;
import com.ethereal.witch.models.user.UserClient;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.service.exception.PasswordInvalidException;
import com.ethereal.witch.service.exception.UniqueViolationExeception;
import com.ethereal.witch.service.exception.UnnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;
    private AccessUser getUserRole(HttpServletRequest request){
        return iUserRepository.findByid((Long) request.getAttribute("idUser")).getAccess();
    }
    @Transactional
    public UserClient saveUser (UserClient user){
        UserClient newUser = findUsername(user.getUsername());
        if (newUser != null){
            throw new UniqueViolationExeception(String.format("User {%s} already created.",user.getUsername()));
        }else{
            return iUserRepository.save(user);
        }
    }
    @Transactional(readOnly = true)
    public UserClient findUsername (String name){
        try{
        return iUserRepository.findByUsername(name);
        }catch (EntityNotfoundException ex){
            throw new EntityNotfoundException(String.format("User {%s} not found.", name));
        }
    }
    @Transactional(readOnly = true)
    public UserClient findById(Long id){
            return iUserRepository.findById(id).orElseThrow(()->
                    new EntityNotfoundException(String.format("User id: %s not exists",id)));

    }
    @Transactional(readOnly = true)
    public List<UserClient> findAllUser(HttpServletRequest request){
        if (getUserRole(request) != AccessUser.ADMIN) {
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }else{
        return iUserRepository.findAll();
        }
    }
    @Transactional(readOnly = true)
    public List<UserClient> findiLike(String name){
        return iUserRepository.findUserlike(name);
    }
    @Transactional
    public UserClient ChangePassword(Long id, String currentPassword, String newPassword, String confirmPassword){
        if (!(newPassword.equals(confirmPassword))){
            throw new PasswordInvalidException("New password does not match password confirmation.");
        }
        UserClient user = iUserRepository.findByid(id);
        if (!(BCrypt.verifyer().verify(currentPassword.toCharArray(),user.getPassword()).verified)){
            System.out.println("user - " + user.getPassword() + " current - " + currentPassword );
            throw new PasswordInvalidException("Your password does not match.");
        }

        String cryptPassword = BCrypt.withDefaults().hashToString(12,newPassword.toCharArray());
        user.setPassword(cryptPassword);
        iUserRepository.save(user);
        return user;
    }
    @Transactional
    public void deleteUser(Long id){
        iUserRepository.delete(this.findById(id));
    }
}