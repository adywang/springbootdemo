package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
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
    @RequestMapping("/user/list")
    public List<User> getList(){
      return   userMapper.GetUsers();
    }
    @RequestMapping("/role/list")
    public List<Role> getRoleList(){
        Role role=roleMapper.getById("4D61B341-1552-4A28-A320-20357592CE6A");
        return   roleMapper.getAll();
    }
}
