package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.BlogService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @RequestMapping("/user/list")
    public List<User> getList(){
      return   userMapper.GetUsers();
    }
    @RequestMapping("/role/list")
    public List<Role> getRoleList(){
         blogService.addBlog();
        return   roleMapper.getAll();

    }
}
