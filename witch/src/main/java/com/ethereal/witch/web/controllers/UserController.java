package com.ethereal.witch.web.controllers;

import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.models.user.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.service.UserService;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.web.dto.UserCreateDto;
import com.ethereal.witch.web.dto.UserPasswordDto;
import com.ethereal.witch.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid UserCreateDto userDto) {
        var cryptPassword = BCrypt.withDefaults().hashToString(12, userDto.getPassword().toCharArray());
        userDto.setPassword(cryptPassword);
        User user = userService.saveUser(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));


        /*Map<String, String> flashMsg = new HashMap<>();
        flashMsg.put("msg", "User " + userDto.getUsername() + " User created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashMsg);*/
    }

    @GetMapping("/all")
    public ResponseEntity<Object> index() {
        List<User> allUser = userService.findAllUser();

        if (allUser.isEmpty()) {
            throw new EntityNotfoundException("Users not found.");
        }
        return ResponseEntity.status(201).body(UserMapper.toListDto(allUser));
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<Object> findId(@PathVariable Long id) {
        User userId = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(userId));
    }

    @GetMapping("/")
    public ResponseEntity<Object> findByNameIlike(@RequestParam("user") String user) {
        var userIlike = userService.findiLike(user);

        if (userIlike.isEmpty()) {
            throw new EntityNotfoundException(String.format("User not found."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userIlike);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordDto dto) {
        userService.ChangePassword(id,dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg","Password changed successfuly.");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        userService.deleteUser(id);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", user.getName() + " has been successfuly deleted.");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
