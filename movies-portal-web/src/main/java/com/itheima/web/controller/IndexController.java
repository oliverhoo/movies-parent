package com.itheima.web.controller;

import com.itheima.domain.Comment;
import com.itheima.domain.Movies;
import com.itheima.service.CommentService;
import com.itheima.service.MoviesService;
import com.itheima.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
public class IndexController {
    @Autowired
    private MoviesService moviesService;
    @GetMapping("/movies/findMovies")
    public Map<String , List<Movies>> findMovies(){
        //查询分类和movies对应的关系
        Map<String , List<Movies>> map = moviesService.findMovies();
        return map;
    }
    @GetMapping("/movies/findMoviesById")
    public Movies findMoviesById(Integer id){
        return moviesService.findMoviesById(id);
    }
    @Autowired
    private CommentService commentService;
    @GetMapping("/comment/findCommentByMoviesId")
    public List<Comment> findComments(Integer id){
        return commentService.findComments(id);
    }
    @PostMapping("/comment/save" )
    public Boolean saveComments (@RequestBody Comment comment,@RequestHeader("Authorization") String token){
        Map map = JwtUtil.parseToken(token);
        comment.setCreatorName(map.get("username").toString());
        comment.setCreatorId(Integer.valueOf(map.get("id").toString()));
        comment.setTime(new Date());
        commentService.saveComments(comment);
        return true;
    }

}
