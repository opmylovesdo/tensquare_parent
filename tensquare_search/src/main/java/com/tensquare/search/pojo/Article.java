package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.search.pojo
 * @version: 1.0
 */
@Document(indexName = "tensquare_article", type = "article")
@Data
public class Article {

    @Id
    private String id;//ID
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", type = FieldType.text)
    private String title;//标题
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", type = FieldType.text)
    private String content;//文章正文
    private String state;//审核状态
}
