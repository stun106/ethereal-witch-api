package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.IUserRepository;
import com.ethereal.witch.models.user.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ethereal.witch.models.user.UserRecordDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserRepository iuserRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid UserRecordDto userDto) {
        var userEntity = new User();
        BeanUtils.copyProperties(userDto, userEntity);

        var user = this.iuserRepository.findByUsername(userEntity.getUsername());
        if (user != null) {
            Map<String, String> flashMsg = new HashMap<>();
            flashMsg.put("error", "Algo deu errado, verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashMsg);
        }
        var cryptPassword = BCrypt.withDefaults().hashToString(12, userEntity.getPassword().toCharArray());
        userEntity.setPassword(cryptPassword);

        this.iuserRepository.save(userEntity);

        Map<String, String> flashMsg = new HashMap<>();
        flashMsg.put("msg", "Usuario " + userEntity.getUsername() + " criado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(flashMsg);
    }

    @GetMapping("/all")
    public ResponseEntity index() {
        var allUser = this.iuserRepository.findAll();

        if (allUser.isEmpty()) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Não a usuarios registrados!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        return ResponseEntity.status(201).body(allUser);
    }

    @GetMapping("/single/")
    public ResponseEntity<Object> findId(@RequestParam("id") Long id) {
        Optional<User> userId = Optional.ofNullable(iuserRepository.findByid(id));

        if (userId.isEmpty()) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Usuario não existe!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @GetMapping("/")
    public ResponseEntity findByNameIlike(@RequestParam("user") String user) {
        var userIlike = this.iuserRepository.findUserIlike(user);

        if (userIlike.isEmpty()) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Usuario não existe!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userIlike);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid UserRecordDto userDto) {
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        Optional<User> iduser = Optional.ofNullable(this.iuserRepository.findByid(id));
        if (iduser.isEmpty()) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "usuario não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        user.setId(iduser.get().getId());
        this.iuserRepository.save(user);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Usuario: " + user.getUsername() + " Fez alterações com sucesos!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity destroy(@PathVariable("id") Long id) {
        Optional<User> user = Optional.ofNullable(this.iuserRepository.findByid(id));
        if (user.isEmpty()) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Usuario não Existe!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        this.iuserRepository.delete(user.get());
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", user.get().getName() + " foi deletado com sucesso!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
    }
}
