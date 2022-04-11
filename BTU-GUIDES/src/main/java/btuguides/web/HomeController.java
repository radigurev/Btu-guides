package btuguides.web;

import btuguides.models.view.ReCaptchaResponse;
import btuguides.service.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    private String message="";

    private final PartnerService partnerService;
    private final WorkerService workerService;
    private final TripService tripService;
    private final TranslateService translateService;
    private final CourseService courseService;
    private final RestTemplate restTemplate;
    public HomeController(PartnerService partnerService, WorkerService workerService, TripService tripService, TranslateService translateService, CourseService courseService, RestTemplate restTemplate) {
        this.partnerService = partnerService;
        this.workerService = workerService;
        this.tripService = tripService;
        this.translateService = translateService;
        this.courseService = courseService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("verification",message);
        message="";
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("partners",partnerService.find());
        return "about-us";
    }

    @GetMapping("/translators")
    public String translatorsPage(Model model) {
        model.addAttribute("translators",workerService.find());
        return "translators-page";
    }

    @GetMapping("/consult")
    public String consultPage() {
        return "consult-page";
    }
    @GetMapping("/courses")
    public String coursePage(Model model) {
        model.addAttribute("courses",courseService.find());
        return "courses-page";
    }
    @GetMapping("/offers")
    public String offersPage(Model model) {
        model.addAttribute("trips",tripService.find())
                .addAttribute("translate",translateService.find());
        return "translate-offers-page";
    }
    @PostMapping("/")
    public String postIndexPage(@RequestParam (name="g-recaptcha-response") String captchaResponse) {
        String url="https://www.google.com/recaptcha/api/siteverify";
        String param=String.format("?secret=6LcJ3GMfAAAAAOjbQgfsN5hrMRcKMES2qWwwNBIB&response=%s",captchaResponse);

        ReCaptchaResponse reCaptchaResponse=restTemplate.exchange(url+param, HttpMethod.POST,null,ReCaptchaResponse.class).getBody();


        if (reCaptchaResponse != null && reCaptchaResponse.isSuccess()) {
            message="Запитването е успешно изпратено";
            return "redirect:/#contact";
        }

        message = "Моля отбележете проверката";
        return "redirect:/#contact";
    }
}

