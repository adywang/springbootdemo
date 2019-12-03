package com.example.demo.mapper;

import com.example.demo.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMapper {
    List<Role> getAll();
    Role getById(String id);
}
