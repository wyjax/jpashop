package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        // home controller에 대한 것이 찍히게 된다.
        log.info("home controller");
        // home.html로 찾아간다.
        return "home";
    }
}
