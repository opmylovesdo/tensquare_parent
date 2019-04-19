package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/18
 * @Description: com.tensquare.spit.controller
 * @version: 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 吐槽评论点赞
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result updateThumpUp(@PathVariable String spitId){

        //判断用户是否点过赞
        String userId = "1111";//测试使用, 后面替换成当前登录用户
        if(redisTemplate.opsForValue().get("thumpup_"+userId + "_" + spitId) != null){
            return new Result(false, StatusCode.REPERROR, "不要重复点赞");
        }

        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumpup_"+userId + "_" + spitId, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 根据parentId 获取吐槽列表
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByParentId(@PathVariable("parentid") String parentId,
                                 @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }


    /**
     *   查询全部数据
     *   @return
     *  
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功",
                spitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @return
     * @param id ID
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /**
     * 增加
     *
     * @param spit
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 更新
     *
     * @param spit
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spitService.udpate(spit);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}



