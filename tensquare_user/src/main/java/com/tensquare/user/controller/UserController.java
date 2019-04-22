package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    @PutMapping("/{userid}/{friendid}/{num}")
    public void updateFansCountAndFollowerCount(@PathVariable String userid,
                                                @PathVariable String friendid,
                                                @PathVariable int num){
        userService.updateFansCountAndFollowerCount(userid, friendid, num);

    }


    @PostMapping("/login")
    public Result login(@RequestBody User user){

        user = userService.login(user);

        if(user != null){
            String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("roles", "user");
            return new Result(true, StatusCode.OK, "登录成功", map);
        }

        return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误");

    }


    @PostMapping("/register/{checkCode}")
    public Result register(@RequestBody User user, @PathVariable String checkCode){
        //从缓存中获取验证码
        String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkcode_" + user.getMobile());

        if(checkcodeRedis.isEmpty()){
            return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
        }

        if(!checkcodeRedis.equals(checkCode)){
            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
        }

        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }


    /**
     * 发送短信验证
     * @param mobile
     * @return
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile){
        String checkcode = RandomStringUtils.randomNumeric(6);
        //String checkcodeStr = (String) redisTemplate.opsForValue().get("checkcode_" + mobile);
        //缓存中存一份  校验用户填的校验码是否正确
        //if(StringUtils.isNotEmpty(checkcodeStr)) {
        redisTemplate.opsForValue().set("checkcode_" + mobile, checkcode, 5, TimeUnit.HOURS);
            //return new Result(false, StatusCode.REPERROR, "短信已发送");
        //}

        //给用户发一份
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);

        //rabbitTemplate.convertAndSend("sms", map);

        //控制台显示一份 方便测试
        System.out.println("此次的验证码为:" + checkcode);
        return new Result(true, StatusCode.OK, "发送短信成功");
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        System.out.println("findAll");
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {

        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
