package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.cache.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.SchemaOutputResolver;
import java.util.UUID;

/**
 * @Auther: Aspen
 * @Created: 2019/1/8 0008.
 */
@RestController
@RequestMapping("/api/product")
public class RedisController {


    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/v1/redis-set")
    public Response redisSet(@RequestParam("productId") Long productId) throws Exception {
        redisUtil.set("test", "productId : " + productId);
        return new Response();
    }

    @GetMapping("/v1/redis-get")
    public Response redisGet(@RequestParam("productId") Long productId) throws Exception {
        String test = (String) redisUtil.get("test");
        return new Response(test);
    }


}
