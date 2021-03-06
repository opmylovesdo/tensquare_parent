package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.article.service
 * @version: 1.0
 */
@Transactional
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment){
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }

    public List<Comment> findByArticleId(String articleId){
        return commentDao.findByArticleid(articleId);
    }
}
