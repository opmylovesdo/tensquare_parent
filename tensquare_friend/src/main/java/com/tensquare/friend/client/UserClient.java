package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.friend.client
 * @version: 1.0
 */
@FeignClient("tensquare-user")
public interface UserClient {

    @PutMapping("/user/{userid}/{friendid}/{num}")
    void updateFansCountAndFollowerCount(@PathVariable("userid") String userid,
                                                @PathVariable("friendid") String friendid,
                                                @PathVariable("num") int num);
}
