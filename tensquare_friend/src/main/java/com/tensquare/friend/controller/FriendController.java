package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.friend.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;


    @Autowired
    private HttpServletRequest request;



    @DeleteMapping("/{friendid}")
    public Result delete(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(null == claims){
            //当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }

        friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 类型 1:喜欢 2：不喜欢
     *
     * @param friendid
     * @param type
     * @return
     */
    @RequestMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //验证是否登录, 并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims) {
            //当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }

        //判断是添加好友还是添加非好友
        if (StringUtils.isNotEmpty(type)) {
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(claims.getId(), friendid);

                if(0 == flag){
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }

                if(1 == flag){
                    return new Result(true, StatusCode.OK, "添加成功");
                }

            } else if ("2".equals(type)) {
                //添加非好友
                int flag = friendService.addNoFriend(claims.getId(), friendid);

                if(0 == flag){
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                }

                if(1 == flag){
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
        }


        return new Result(false, StatusCode.ERROR, "参数异常");
    }
}
