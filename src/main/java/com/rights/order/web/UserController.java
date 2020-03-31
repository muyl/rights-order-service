package com.rights.order.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 拓仲 on 2020/3/15
 */
@RestController
@RequestMapping("/api/web/")
public class UserController {

    @RequestMapping("/queryUserName.json")
    public String queryUserName(){
        return UUID.randomUUID().toString();
    }
}
