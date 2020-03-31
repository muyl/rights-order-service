package com.rights.order.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 拓仲 on 2020/3/29
 */
@RestController
public class AlarmController {

    @GetMapping("/timeout")
    public String timeout(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout";
    }
}
