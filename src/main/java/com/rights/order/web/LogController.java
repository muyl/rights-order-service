package com.rights.order.web;

import com.rights.order.message.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 拓仲 on 2020/3/28
 */
@RestController
public class LogController {
    private Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    AsyncTaskExecutor asyncServiceExecutor;

    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/test")
    @Async
    public void test() throws InterruptedException {
        LOGGER.info("TestController /test invoke");
        Thread.sleep(1500);
        asyncServiceExecutor.execute(() -> LOGGER.info("Another thread running"));
        messageProducer.sendMessage("test.queue","send message");
    }
}
