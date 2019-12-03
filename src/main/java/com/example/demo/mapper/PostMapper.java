package com.example.demo.mapper;

import com.example.demo.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    public List<Post> getAll();
    public  Post getById(String id);
    void  addEntity(Post post);
}
