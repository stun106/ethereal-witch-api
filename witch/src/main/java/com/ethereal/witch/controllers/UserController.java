package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.IUserRepository;
import com.ethereal.witch.models.user.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserRepository iuserRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody User userEntity) {

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
    public ResponseEntity<List<User>> index() {
        var allUser = this.iuserRepository.findAll();
        return ResponseEntity.status(201).body(allUser);
    }

    @GetMapping("/single/")
    public ResponseEntity<User> findId(@RequestParam("id") Long id) {
        var userId = iuserRepository.findByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findByNameIlike(@RequestParam("user") String user) {
        var userIlike = this.iuserRepository.findUserIlike(user);
        return ResponseEntity.status(HttpStatus.OK).body(userIlike);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, HttpServletRequest request, @RequestBody User user) {
        User iduser = this.iuserRepository.findByid(id);
        if (iduser == null) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "usuario não encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashmsg);
        }
        user.setId(iduser.getId());
        iduser.setName(user.getName());
        iduser.setPassword(user.getPassword());
        iduser.setUsername(user.getUsername());
        this.iuserRepository.save(iduser);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Usuario: " + user.getUsername() + " Fez alterações com sucesos!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
