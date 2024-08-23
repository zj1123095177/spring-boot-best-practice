package cn.javastack.springboot.jpa.controller;

import cn.javastack.springboot.jpa.entity.UserDO;
import cn.javastack.springboot.jpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/info/{id}")
    public UserDO getUserInfo(@PathVariable("id") long id){
        return userRepository.findById(id).orElseGet(UserDO::new);
    }

    @PostMapping("/user/info")
    public UserDO addUserInfo(@RequestBody UserDO userDO){
        return userRepository.save(userDO);
    }

}