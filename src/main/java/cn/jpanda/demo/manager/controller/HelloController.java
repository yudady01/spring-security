package cn.jpanda.demo.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        log.info("请求进来了....");
        return "Hello, Spring Boot 2!" + "你好" ;
    }

    @RequestMapping("/hello")
    public String handle01(@RequestParam("name") String name) {
        log.info("请求进来了....");
        return "Hello, Spring Boot 2!" + "你好：" + name;
    }

}
