package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/18
 * @Description: 吐槽数据访问层
 * @version: 1.0
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    Page<Spit> findByParentid(String parentId, Pageable pageable);
}
