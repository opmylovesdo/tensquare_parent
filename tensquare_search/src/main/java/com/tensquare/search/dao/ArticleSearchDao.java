package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.search.dao
 * @version: 1.0
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article, String> {

    /**
     *  检索
     *  @param
     *  @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
