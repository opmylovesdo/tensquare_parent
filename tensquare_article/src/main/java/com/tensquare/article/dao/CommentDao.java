package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.article.dao
 * @version: 1.0
 */
public interface CommentDao extends MongoRepository<Comment, String> {

    List<Comment> findByArticleid(String articleId);
}
