package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.MoviesDao;
import com.itheima.domain.Category;
import com.itheima.domain.Movies;
import com.itheima.domain.MoviesExample;
import com.itheima.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoviesServiceImpl implements MoviesService {
    @Autowired
    private MoviesDao moviesDao;
    @Override
    public PageInfo<Movies> findList(MoviesExample example,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Movies> moviesList = moviesDao.selectByExample(example);
        return new PageInfo<>(moviesList,3);
    }

    @Override
    public void save(Movies movies) {
        moviesDao.insertSelective(movies);
    }

    @Override
    public void update(Movies movies) {
        moviesDao.updateByPrimaryKeySelective(movies);
    }

    @Override
    public Movies findById(Integer id) {
        return moviesDao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        moviesDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id : ids) {
            moviesDao.deleteByPrimaryKey(id);
        }
    }

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Map<String, List<Movies>> findMovies() {
        List<Category> categoryList = categoryDao.selectByExample(null);
        //3.创建map
        Map<String, List<Movies>> map= new HashMap<>();
        for (Category category : categoryList) {
            //2.根据分类的id查询到对应的影视数据
            MoviesExample moviesExample = new MoviesExample();
            MoviesExample.Criteria criteria = moviesExample.createCriteria();
            criteria.andCidEqualTo(category.getId());
            //根据cid查询影视数据
            PageHelper.startPage(1 , 6);
            //固定查询前六条
            List<Movies> moviesList = moviesDao.selectByExample(moviesExample);

            //4.建立起 分类和影视数据的关系
            map.put(category.getCategoryName() , moviesList);
        }
        return map;
    }

    @Override
    public Movies findMoviesById(Integer id) {
        return moviesDao.selectByPrimaryKey(id);
    }
}
