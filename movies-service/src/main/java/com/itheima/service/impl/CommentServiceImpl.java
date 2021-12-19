package com.itheima.service.impl;

import com.itheima.dao.CommentDao;
import com.itheima.domain.Comment;
import com.itheima.domain.CommentExample;
import com.itheima.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public void saveComments(Comment comment) {
        commentDao.insertSelective(comment);
    }

    @Override
    public List<Comment> findComments(Integer id) {
        CommentExample example = new CommentExample();
        example.setOrderByClause("time desc");
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andMoviesIdEqualTo(id);
        List<Comment> comments = commentDao.selectByExample(example);
        return comments;
    }
}
