package com.task.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/keyword")
    public String keyword() {
        return "keyword";
    }

}
