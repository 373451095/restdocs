package cn.csg.restdocs.controller;

import cn.csg.restdocs.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/test/{id}")
    public String testRestDocs(
            @PathVariable("id") Integer id){
        return "hello Rest Docs :"+id;
    }

    @GetMapping("/user")
    public String user(
            @RequestParam String page,
            @RequestParam String per_page){
        return "page:"+page+"  per_page:"+per_page;
    }

    @GetMapping("/")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/getUser")
    public User getUser(){
        User user = new User();
        user.setId("1");
        user.setName("wandoujia");
        user.setEmail("18221820094@163.com");
        return user;
    }

    @GetMapping("/alpha")
    public String alpha(){
        return "hello alpha";
    }

    @GetMapping("/bravo")
    public String bravo(){
        return "hello bravo";
    }
}
