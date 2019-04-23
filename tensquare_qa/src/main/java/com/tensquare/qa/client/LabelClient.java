package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.qa.client
 * @version: 1.0
 */
@FeignClient(value="tensquare-base", fallback = LabelClientImpl.class)
public interface LabelClient {

    @GetMapping("/label/{id}")
    Result findById(@PathVariable("id") String id);
}
