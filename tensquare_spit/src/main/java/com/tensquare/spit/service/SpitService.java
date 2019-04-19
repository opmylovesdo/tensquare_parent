package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/18
 * @Description: com.tensquare.spit.service
 * @version: 1.0
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 吐槽点赞
     * @param spitId
     */
    public void updateThumbup(String spitId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

    /**
     * 吐槽点赞  实现简单 ,但是执行效率并不高，因为我只需要将点赞数加1就可
        以了，没必要查询出所有字段修改后再更新所有字段
     * @param spitId
     */
    public void updateThumbup2(String spitId){
        Optional<Spit> optionalSpit = spitDao.findById(spitId);
        optionalSpit.ifPresent((s) -> {
            if(s.getThumbup() == null) s.setThumbup(0);
            s.setThumbup(s.getThumbup() + 1);
            spitDao.save(s);
        });
    }


    /**
     * 根据上级ID查询吐槽列表
     * @param parentid
     * @param page
     * @param size
     * @return
     *      
     */
    public Page<Spit> findByParentid(String parentId, int page, int size) {
        return spitDao.findByParentid(parentId, PageRequest.of(page - 1, size));
    }


    /**
     * 查询全部记录    
     *
     * @return    
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据主键查询实体    
     *
     * @return    
     * @param id    
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 增加    
     *
     * @param spit    
     *  
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布时间
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态

        if(spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    /**
     * 修改    
     *
     * @param spit    
     *  
     */
    public void udpate(Spit spit) {
        spitDao.save(spit);
    }

    /**
     *  删除    
     *  @param id    
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }
}
