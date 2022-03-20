package btuguides.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about-us";
    }

    @GetMapping("/translators")
    public String translatorsPage() {
        return "translators-page";
    }

    @GetMapping("/consult")
    public String consultPage() {
        return "consult-page";
    }
    @GetMapping("/courses")
    public String coursePage() {
        return "courses-page";
    }
    @GetMapping("/offers")
    public String offersPage() {
        return "translate-offers-page";
    }
}
