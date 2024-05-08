package com.ms.user.controller;

import com.ms.user.dto.UserRecordDto;
import com.ms.user.model.Address;
import com.ms.user.model.User;
import com.ms.user.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRecordDto userDto){
        var user = new User();
        var address = new Address();
        BeanUtils.copyProperties(userDto,user);
        BeanUtils.copyProperties(userDto,address);

        return ResponseEntity.status(HttpStatus.CREATED).body( userService.save(user,address));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findUser(@PathVariable UUID id){
        var user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable UUID id) {
        userService.activateUser(id);
        return ResponseEntity.ok("Usuário ativado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<User>> findUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());

    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.inactivateUser(id);
        return ResponseEntity.noContent().build();
    }

}
