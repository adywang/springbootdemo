package com.example.demo.mapper;

import com.example.demo.entity.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    public List<Blog> getAll();
    public Blog getById(String id);
    void  addEntity(Blog blog);
}
