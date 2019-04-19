package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.article.controller
 * @version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功");
    }

    @GetMapping("/article/{articleId}")
    public Result findByArticleId(@PathVariable String articleId){
        return new Result(true, StatusCode.OK, "查询成功",
                commentService.findByArticleId(articleId));
    }
}
