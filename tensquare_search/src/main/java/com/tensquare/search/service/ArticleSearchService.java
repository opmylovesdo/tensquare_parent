package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.search.service
 * @version: 1.0
 */
@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     *  增加文章
     *  @param article
     */
    public void add(Article article) {
        articleSearchDao.save(article);
    }

    /**
     * 根据关键词查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> searchByKeywords(String keywords, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleSearchDao.findByTitleOrContentLike(keywords, keywords, pageRequest);
    }
}
