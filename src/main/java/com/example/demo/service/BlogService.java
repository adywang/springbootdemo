package com.example.demo.service;

import com.example.demo.entity.Blog;
import com.example.demo.entity.Post;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private PostMapper postMapper;
    @Transactional
    public void  addBlog()  {
        Blog blog=new Blog();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String blogid=UUID.randomUUID().toString();
        blog.setId(blogid);
        blog.setTitle("java入门经典");
        blog.setCreateTime(now);
        blogMapper.addEntity(blog);
        Post post=new Post();
        post.setBlogId(blogid);
        post.setId(UUID.randomUUID().toString());
        post.setCreateTime(now);
        post.setTitle("java 入门经典设置");
        postMapper.addEntity(post);
    }
}
