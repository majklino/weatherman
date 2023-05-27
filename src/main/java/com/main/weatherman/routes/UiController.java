package com.main.weatherman.routes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UiController {
    
    @GetMapping("")
    public String hello(Model model){
        model.addAttribute("msg", "Hello there");
        return "index";
    }
}
