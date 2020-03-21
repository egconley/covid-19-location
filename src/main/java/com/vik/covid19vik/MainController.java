package com.vik.covid19vik;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MainController {
    private static final String template = "Hello, %s!";

    @GetMapping("/greeting") // ensures HTTP GET requests to /greeting mapped to greeting() method
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        // @RequestParam binds value of query string parameter name into name parameter of greeting(); if name absent, "World" used instead
        model.addAttribute("name", name);
        return "greeting";
    }
}
