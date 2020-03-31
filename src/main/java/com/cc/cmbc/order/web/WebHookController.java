package com.cc.cmbc.order.web;

import com.cc.cmbc.order.domain.AlarmMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 拓仲 on 2020/3/29
 */
@RestController
public class WebHookController {

    private List<AlarmMessage> lastList = new ArrayList<>();

    @PostMapping("/webHook")
    public void webHook(@RequestBody List<AlarmMessage> alarmMessageList) {
        lastList = alarmMessageList;
    }

    @GetMapping("/show")
    public List<AlarmMessage> show(){
        return lastList;
    }
}
