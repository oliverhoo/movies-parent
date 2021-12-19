package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Movies;
import com.itheima.domain.MoviesExample;

import java.util.List;
import java.util.Map;

public interface MoviesService {
    PageInfo<Movies> findList(MoviesExample example,Integer pageNum,Integer pageSize);

    void save(Movies movies);

    void update(Movies movies);

    Movies findById(Integer id);

    void deleteById(Integer id);

    void deleteByIds(Integer[] ids);

    Map<String, List<Movies>> findMovies();

    Movies findMoviesById(Integer id);
}
