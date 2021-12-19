package com.itheima.service;

import com.itheima.domain.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> findComments(Integer id);
    public void saveComments(Comment comment);
}
