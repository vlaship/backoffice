package dev.vlaship.backoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenApiController {
    @GetMapping("/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}
