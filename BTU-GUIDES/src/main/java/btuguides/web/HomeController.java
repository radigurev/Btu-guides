package btuguides.web;

import btuguides.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PartnerService partnerService;
    private final WorkerService workerService;
    private final TripService tripService;
    private final TranslateService translateService;
    private final CourseService courseService;
    public HomeController(PartnerService partnerService, WorkerService workerService, TripService tripService, TranslateService translateService, CourseService courseService) {
        this.partnerService = partnerService;
        this.workerService = workerService;
        this.tripService = tripService;
        this.translateService = translateService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index() {
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
}
