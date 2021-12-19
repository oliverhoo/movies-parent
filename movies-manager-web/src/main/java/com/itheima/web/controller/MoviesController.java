package com.itheima.web.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.Movies;
import com.itheima.domain.MoviesExample;
import com.itheima.service.MoviesService;
import com.itheima.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController  //@Controller  @ResponseBody 当前类中所有的方法都为异步请求
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    /**
     * 查询影视列表
     * 如果是get请求  不需要加@RequestBody 直接接收即可
     */
    @RequestMapping("/moviesController/findList")
    //@ResponseBody
    public PageInfo<Movies> findList(String cid, String keyword,
                                @RequestParam(defaultValue ="1" ) Integer pageNum,
                                 @RequestParam(defaultValue="5") Integer pageSize)
            {
        //MoviesExample 用于封装条件的对象
        MoviesExample example = new MoviesExample();
        //拼接条件
        MoviesExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(cid)) {//cid不为空
            criteria.andCidEqualTo(Integer.valueOf(cid));  //拼接cid条件
        }

        if (StrUtil.isNotEmpty(keyword)) {//搜索条件不为空
            criteria.andKeywordLike("%" + keyword + "%");  //拼接搜索条件
        }
        //根据条件 查询数据
         PageInfo<Movies> pageInfo = moviesService.findList(example, pageNum, pageSize);
         return pageInfo;
    }
   /* @PostMapping("/file/upload")
    //@ResponseBody
    public String upload(MultipartFile uploadFile) throws IOException {
        System.out.println("文件上传");
        if(uploadFile!= null){ //对象不为空 上传图片
            String filePath = OssUtil.upload(uploadFile.getOriginalFilename(), uploadFile.getInputStream());
            return filePath;  //将图片的路径 记录下来 并返回给前端 以保证 一会添加整条数据的时候有路径
        }
        return null;
    }*/
   @PostMapping("/file/upload")
   //@ResponseBody
   public String upload(MultipartFile uploadFile) throws IOException {
       System.out.println("文件上传");
       if(uploadFile!= null){ //对象不为空 上传图片
           String filePath = OssUtil.upload(uploadFile.getOriginalFilename(), uploadFile.getInputStream());
           return filePath;  //将图片的路径 记录下来 并返回给前端 以保证 一会添加整条数据的时候有路径
       }
       return null;
   }

    @PostMapping("/movies") // rest 风格  保存是post
    public boolean saveOrUpdate(@RequestBody Movies movies) throws IOException {

        try {
            if(movies.getId()==null){
                //新增
                movies.setShowTime(new Date()); //时间自己设定
                moviesService.save(movies);
            }else{
                //修改
                moviesService.update(movies);
            }
            return true;  //正常操作 返回true
        } catch (Exception e) {
            e.printStackTrace();
            return false; //报错情况 返回false
        }
    }
    @RequestMapping("/movies/{id}")
    public Movies fingById(@PathVariable("id")Integer id){
        return moviesService.findById(id);
    }
    @DeleteMapping("/movies/{id}")
    public Boolean deleteById(@PathVariable("id")Integer id){
        try {
            moviesService.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @DeleteMapping("/movies/deleteByIds")
    public Boolean deleteByIds(Integer[] ids){
        try {
            moviesService.deleteByIds(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}