package com.onsil.onsil.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class HomeController {
    @GetMapping("/index")
    public String index() {
        return "index/index";
    }
}
