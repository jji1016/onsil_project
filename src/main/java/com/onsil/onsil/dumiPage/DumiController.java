package com.onsil.onsil.dumiPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DumiController {

    @GetMapping("/corporate")
    public String corporate() {
        return "dumiPage/corporate";
    }

    @GetMapping("/onsilfaq")
    public String onsilfaq() {
        return "dumiPage/onsilfaq";
    }
}
