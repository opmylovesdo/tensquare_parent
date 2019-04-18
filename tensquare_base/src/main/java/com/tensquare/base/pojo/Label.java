package com.tensquare.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/16
 * @Description: com.tensquare.base.pojo
 * @version: 1.0
 */
@Entity
@Table(name = "tb_label")
@Data
public class Label {

    @Id
    private String id;//
    private String labelname;//标签名称
    private String state;//状态
    private Long count;//使用数量
    private Long fans;//关注数
    private String recommend;//是否推荐
}
