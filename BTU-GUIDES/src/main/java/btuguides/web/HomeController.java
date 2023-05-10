package btuguides.web;

import btuguides.models.binding.ContactBindingModel;
import btuguides.models.view.ReCaptchaResponse;
import btuguides.service.*;
//
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private String message = " ";

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
        model.addAttribute("verification", message);
        message = "";
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("partners", partnerService.find("partner"));
        model.addAttribute("otherPartners", partnerService.find("bigPartner"));
        return "about-us";
    }

    @GetMapping("/translators")
    public String translatorsPage(Model model) {
        model.addAttribute("translators", workerService.find());
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
        model.addAttribute("courses", courseService.find("Seminar"))
                .addAttribute("title", "Семинари")
                .addAttribute("cover", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652195904/btu-guides/coursesSelectPage/seminar/pexels-pixabay-159213_cikzco.jpg");
        return "courses-page";
    }

    @GetMapping("/courses/course")
    public String coursesPage(Model model) {
        model.addAttribute("courses", courseService.find("Course"))
                .addAttribute("title", "Курсове")
                .addAttribute("cover", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652195885/btu-guides/coursesSelectPage/Courses/pexels-pixabay-256395_noqdco.jpg");
        return "courses-page";
    }

    @GetMapping("/courses/training")
    public String coursesTrainingPage(Model model) {
        model.addAttribute("courses", courseService.find("Training"))
                .addAttribute("title", "Обучения")
                .addAttribute("cover", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652195973/btu-guides/coursesSelectPage/training/pexels-valentin-monov-6658672_1_cwmfct.jpg");
        return "courses-page";
    }

    @GetMapping("/offers")
    public String offersPage() {
        return "trip-select-page";
    }

    @GetMapping("/offers/bulgaria")
    public String offersBulgariaPage(Model model) {
        model.addAttribute("trips", tripService.find("Bulgaria"))
                .addAttribute("translate", translateService.find())
                .addAttribute("title", "България")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194541/btu-guides/tripSelectPage/BulgarianTrips/pexels-valentin-monov-6658672_i8ngez.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/offers/tours")
    public String offersToursPage(Model model) {
        model.addAttribute("trips", tripService.find("Tour"))
                .addAttribute("translate", translateService.find())
                .addAttribute("title", "България")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194541/btu-guides/tripSelectPage/BulgarianTrips/pexels-valentin-monov-6658672_i8ngez.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/offers/international")
    public String offersInternationallyPage(Model model) {
        model.addAttribute("trips", tripService.find("Intr"))
                .addAttribute("translate", translateService.find())
                .addAttribute("title", "Чужбина")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194425/btu-guides/tripSelectPage/internationallyTrips/pexels-pixabay-161853_e4ruzi.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/mail/{id}")
    public String getMailPage(@PathVariable String id, Model model) {

        model.addAttribute("Id",id);

        return "mail";
    }

    @PostMapping("/mail/{id}")
    public String sendMail(@PathVariable String id,@RequestParam(name = "g-recaptcha-response") String captchaResponse,ContactBindingModel contactBindingModel, RedirectAttributes redirectAttributes) {

        if(contactBindingModel.isEmpty()) {
            redirectAttributes.addFlashAttribute("contactBindingModel",contactBindingModel);
            return "redirect:/mail/"+id;
        }
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String param = String.format("?secret=6LcJ3GMfAAAAAOjbQgfsN5hrMRcKMES2qWwwNBIB&response=%s", captchaResponse);

        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + param, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        if (reCaptchaResponse != null && reCaptchaResponse.isSuccess()) {
            message = "Запитването е успешно изпратено";

            String to = "coop@btu-guides.org";
            String from = "coop@btu-guides.org";
            String host = "mail.btu-guides.org";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("coop@btu-guides.org", "Proekta6669");

                }

            });
            session.setDebug(true);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                var offer = tripService.findById(id);

                message.setSubject("Запитване за екскурзия "+offer.getTitle());
                message.setText(String.format("Email:%s%n Име:%s %s %s %n Съобщение:%n %s %n Обява:%n %s %n %s"
                        , contactBindingModel.getEmail(), contactBindingModel.getName()
                        ,contactBindingModel.getSecondName(),contactBindingModel.getFamilyName()
                        , contactBindingModel.getText(),offer.getTitle(),offer.getPdfUrl()));
                Transport.send(message);
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            redirectAttributes.addFlashAttribute("message","Успешно подаване на email!");

            return "redirect:/mail/"+id;
        }
        redirectAttributes.addFlashAttribute("message","Моля отбележете проверката!");

        return "redirect:/mail/"+id;
    }

    @PostMapping("/")
    public String postIndexPage(@RequestParam(name = "g-recaptcha-response") String captchaResponse, ContactBindingModel contactBindingModel) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String param = String.format("?secret=6LcJ3GMfAAAAAOjbQgfsN5hrMRcKMES2qWwwNBIB&response=%s", captchaResponse);

        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + param, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        if (reCaptchaResponse != null && reCaptchaResponse.isSuccess() && !contactBindingModel().isEmpty()) {
            message = "Запитването е успешно изпратено";

            String to = "coop@btu-guides.org";
            String from = "coop@btu-guides.org";
            String host = "mail.btu-guides.org";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("coop@btu-guides.org", "Proekta6669");

                }

            });
            session.setDebug(true);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Ново съобщение");
                message.setText(String.format("Email:%s%n Име:%s %s %s %n Съобщение:%n %s", contactBindingModel.getEmail(), contactBindingModel.getName(),contactBindingModel.getSecondName(),contactBindingModel.getFamilyName(), contactBindingModel.getText()));
                Transport.send(message);
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            return "redirect:/#contact";
        } else if (contactBindingModel.isEmpty()) {
            message = "Моля попълнете всички задължителни полета!";
        } else {
            message = "Моля отбележете проверката!";
        }
        return "redirect:/#contact";
    }
}

