package com.skill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";  // returns home.html from templates folder
    }
}
/*package com.skill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";  // returns home.html from templates folder
    }

    @GetMapping("/select-level")
    public String selectLevel() {
        return "select-level";  // returns select-level.html (your form page)
    }

    @PostMapping("/study")
    public String study(@RequestParam("level") String level, Model model) {
        // Add selected level to model to use in the study.html page
        model.addAttribute("selectedLevel", level);

        // TODO: fetch study questions for this level and add to model here

        return "study";  // returns study.html to show study questions
    }
}*/

