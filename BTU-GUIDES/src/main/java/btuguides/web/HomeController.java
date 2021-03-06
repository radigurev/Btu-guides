package btuguides.web;

import btuguides.models.binding.ContactBindingModel;
import btuguides.models.view.ReCaptchaResponse;
import btuguides.service.*;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    private String message=" ";

    private final PartnerService partnerService;
    private final WorkerService workerService;
    private final TripService tripService;
    private final TranslateService translateService;
    private final CourseService courseService;
    private final RestTemplate restTemplate;
    private final JavaMailSender javaMailSender;
    public HomeController(PartnerService partnerService, WorkerService workerService, TripService tripService, TranslateService translateService, CourseService courseService, RestTemplate restTemplate, JavaMailSender javaMailSender) {
        this.partnerService = partnerService;
        this.workerService = workerService;
        this.tripService = tripService;
        this.translateService = translateService;
        this.courseService = courseService;
        this.restTemplate = restTemplate;
        this.javaMailSender = javaMailSender;
    }

    @ModelAttribute
    public ContactBindingModel contactBindingModel() {
        return new ContactBindingModel();
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("verification",message);
        message="";
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("partners",partnerService.find("partner"));
        model.addAttribute("otherPartners",partnerService.find("bigPartner"));
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
    public String coursePage() {
        return "courses-select-page";
    }
    @GetMapping("/courses/seminars")
    public String courseBulgariaPage(Model model) {
        model.addAttribute("courses",courseService.find("Seminar"))
                .addAttribute("title","????????????????")
                .addAttribute("cover","https://res.cloudinary.com/dzqj0bike/image/upload/v1652195904/btu-guides/coursesSelectPage/seminar/pexels-pixabay-159213_cikzco.jpg");
        return "courses-page";
    }
    @GetMapping("/courses/course")
    public String coursesPage(Model model) {
        model.addAttribute("courses",courseService.find("Course"))
                .addAttribute("title","??????????????")
                .addAttribute("cover","https://res.cloudinary.com/dzqj0bike/image/upload/v1652195885/btu-guides/coursesSelectPage/Courses/pexels-pixabay-256395_noqdco.jpg");
        return "courses-page";
    }

    @GetMapping("/courses/training")
    public String coursesTrainingPage(Model model) {
        model.addAttribute("courses",courseService.find("Training"))
                .addAttribute("title","????????????????")
                .addAttribute("cover","https://res.cloudinary.com/dzqj0bike/image/upload/v1652195973/btu-guides/coursesSelectPage/training/pexels-valentin-monov-6658672_1_cwmfct.jpg");
        return "courses-page";
    }

    @GetMapping("/offers")
    public String offersPage() {
       return "trip-select-page";
    }

    @GetMapping("/offers/bulgaria")
    public String offersBulgariaPage(Model model) {
        model.addAttribute("trips",tripService.find("Bulgaria"))
                .addAttribute("translate",translateService.find())
                .addAttribute("title","????????????????")
                .addAttribute("background","https://res.cloudinary.com/dzqj0bike/image/upload/v1652194541/btu-guides/tripSelectPage/BulgarianTrips/pexels-valentin-monov-6658672_i8ngez.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/offers/internationally")
    public String offersInternationallyPage(Model model) {
        model.addAttribute("trips",tripService.find("Intr"))
                .addAttribute("translate",translateService.find())
                .addAttribute("title","??????????????")
                .addAttribute("background","https://res.cloudinary.com/dzqj0bike/image/upload/v1652194425/btu-guides/tripSelectPage/internationallyTrips/pexels-pixabay-161853_e4ruzi.jpg");
        return "translate-offers-page";
    }

    @PostMapping("/")
    public String postIndexPage(@RequestParam (name="g-recaptcha-response") String captchaResponse,ContactBindingModel contactBindingModel) {
        String url="https://www.google.com/recaptcha/api/siteverify";
        String param=String.format("?secret=6LcJ3GMfAAAAAOjbQgfsN5hrMRcKMES2qWwwNBIB&response=%s",captchaResponse);

        ReCaptchaResponse reCaptchaResponse=restTemplate.exchange(url+param, HttpMethod.POST,null,ReCaptchaResponse.class).getBody();

        if (reCaptchaResponse != null && reCaptchaResponse.isSuccess()) {
            message="?????????????????????? ?? ?????????????? ??????????????????";
            SimpleMailMessage msg=new SimpleMailMessage();
            msg.setTo("radi.gurev@gmail.com");
            msg.setSubject(String.format("?????????????????? ???? %s",contactBindingModel.getName()));
            StringBuilder sb=new StringBuilder();
            sb.append(String.format("Email: %s",contactBindingModel.getEmail())).append(System.lineSeparator()).append(contactBindingModel.getText());
            msg.setText(sb.toString());

            javaMailSender.send(msg);
            return "redirect:/#contact";
        }

        message = "???????? ???????????????????? ????????????????????";
        return "redirect:/#contact";
    }
}

