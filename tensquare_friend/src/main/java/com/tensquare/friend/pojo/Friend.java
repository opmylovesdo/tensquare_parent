package com.tensquare.friend.pojo;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.friend.pojo
 * @version: 1.0
 */
@Entity
@Table(name="tb_friend")
// 表名该类是联合主键
@IdClass(Friend.class)
@Data
public class Friend implements Serializable{
    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike;
}
