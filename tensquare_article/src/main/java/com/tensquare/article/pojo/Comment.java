package com.tensquare.article.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/19
 * @Description: com.tensquare.article.pojo
 * @version: 1.0
 */
@Data
public class Comment implements Serializable {

    @Id
    private String _id;  
    private String articleid;  
    private String content;  
    private String userid;  
    private String parentid;  
    private Date publishdate;

}
