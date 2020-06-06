package com.nuofankj.chatRoom.facade;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xifanxiaxue
 * @date 2020/6/6 14:28
 * @desc 测试入口
 */
@RestController
@RequestMapping("/testController")
public class TestFacade {

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}