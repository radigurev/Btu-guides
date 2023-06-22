package btuguides.web;

import btuguides.models.binding.ContactBindingModel;
import btuguides.models.binding.ReservationBindingModel;
import btuguides.models.view.ReCaptchaResponse;
import btuguides.service.*;
//
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.pdf.exporting.xps.schema.Path;
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
    @ModelAttribute
    public ReservationBindingModel reservationBindingModel() {
        return new ReservationBindingModel();
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
                .addAttribute("titleEn", "Bulgaria")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194541/btu-guides/tripSelectPage/BulgarianTrips/pexels-valentin-monov-6658672_i8ngez.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/offers/tours")
    public String offersToursPage(Model model) {
        model.addAttribute("trips", tripService.find("Tour"))
                .addAttribute("translate", translateService.find())
                .addAttribute("title", "Екскурзоводски услуги")
                .addAttribute("titleEn", "Tour guides")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194541/btu-guides/tripSelectPage/BulgarianTrips/pexels-valentin-monov-6658672_i8ngez.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/offers/international")
    public String offersInternationallyPage(Model model) {
        model.addAttribute("trips", tripService.find("Intr"))
                .addAttribute("translate", translateService.find())
                .addAttribute("title", "Чужбина")
                .addAttribute("titleEn", "Abroad")
                .addAttribute("background", "https://res.cloudinary.com/dzqj0bike/image/upload/v1652194425/btu-guides/tripSelectPage/internationallyTrips/pexels-pixabay-161853_e4ruzi.jpg");
        return "translate-offers-page";
    }

    @GetMapping("/mail/{id}")
    public String getMailPage(@PathVariable String id, Model model) {

        model.addAttribute("Id",id);

        return "mail";
    }

    @PostMapping("/mail/{id}")
    public String sendMail(@PathVariable String id,@RequestParam(name = "g-recaptcha-response") String captchaResponse,@CookieValue("language") String language,ReservationBindingModel contactBindingModel, RedirectAttributes redirectAttributes) {

        if(!contactBindingModel.isChecked()) {
            redirectAttributes.addFlashAttribute("contactBindingModel",contactBindingModel);
            redirectAttributes.addFlashAttribute("message","Моля съгласете се с Условията за настаняване и Политика за защита на личните данни!");
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
                    return new PasswordAuthentication("coop@btu-guides.org", "");
                }

            });
            session.setDebug(true);

            try {

                var documentName = Objects.equals(language, "true") ? "BGTemplate.docx" : "ENTemplate.docx";

                var template = Paths.get("root","BTU-GUIDES","src","main","resources","static","templates",documentName);

                Document document = new Document(template.toString());

                Map<String, String> placeHolders = new HashMap<String, String>();
                var offer = tripService.findById(id);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                long diffInMillies = Math.abs(contactBindingModel.getFromDate().getTime() - contactBindingModel.getToDate().getTime());
                placeHolders.put("offerName",offer.getTitle());
                placeHolders.put("fullName",String.format("%s %s %s",contactBindingModel.getFirstName(),contactBindingModel.getMiddleName(),contactBindingModel.getLastName()));
                placeHolders.put("kids", contactBindingModel.getKids());
                placeHolders.put("adults", contactBindingModel.getAdults());
                placeHolders.put("singleRooms", Integer.toString(contactBindingModel.getSingleRooms()));
                placeHolders.put("doubleRooms", Integer.toString(contactBindingModel.getDoubleRooms()));
                placeHolders.put("tripleRooms", Integer.toString(contactBindingModel.getTripleRooms()));
                placeHolders.put("aparments", Integer.toString(contactBindingModel.getApartments()));
                placeHolders.put("nights", Long.toString(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)));
                placeHolders.put("dateFrom", dateFormat.format(contactBindingModel.getFromDate()));
                placeHolders.put("dateTo", dateFormat.format(contactBindingModel.getToDate()));
                placeHolders.put("type", contactBindingModel.getType());
                placeHolders.put("more", contactBindingModel.getMore());
                placeHolders.put("beds", Integer.toString(contactBindingModel.getBonusBeds()));

                replaceTextInDocumentBody(placeHolders,document);
                var path = Paths.get("root","BTU-GUIDES","src","main","resources","static","ForDelete",String.format("%s%s.docx",contactBindingModel.getFirstName(),offer.getId()));
                document.saveToFile(path.toString());
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(String.format("Подадена заявка за резервация от \n Име: %s %s %s \n Email: %s",contactBindingModel.getFirstName(),contactBindingModel.getMiddleName(),contactBindingModel.getLastName(),contactBindingModel.getEmail()));
                MimeBodyPart attachment = new MimeBodyPart();
                var file = new File(path.toString());
                attachment.attachFile(file);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(attachment);
                message.setSubject("Заявка за резервация "+offer.getTitle());

                message.setContent(multipart);

                Transport.send(message);

                message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(contactBindingModel.getEmail()));

                 messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(String.format("Здравейте %s, \n Вашата заявка е успешно получена!",contactBindingModel.getFirstName()));

                 attachment = new MimeBodyPart();
//                 file = new File("C:\\Users\\radig\\OneDrive\\Documents\\Sites\\Btu-guides\\BTU-GUIDES\\src\\main\\resources\\static\\ForDelete\\"+contactBindingModel.getFirstName()+offer.getId()+".docx");
                 file = new File(path.toString());
                attachment.attachFile(file);
//                DataSource source = new FileDataSource("C:\\Users\\radig\\OneDrive\\Documents\\Sites\\Btu-guides\\BTU-GUIDES\\src\\main\\resources\\static\\ForDelete\\"+contactBindingModel.getFirstName()+offer.getId()+".docx");
                 multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(attachment);
                message.setSubject("Успешно получена заявка за резервация "+offer.getTitle());

                message.setContent(multipart);

                Transport.send(message);

                var delete = file.delete();
            } catch (MessagingException mex) {
                mex.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            redirectAttributes.addFlashAttribute("message","Успешно подадена заявка!");

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

                    return new PasswordAuthentication("coop@btu-guides.org", "");

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

     void replaceTextInDocumentBody(Map<String, String> map, Document document){
        for(Section section : (Iterable<Section>)document.getSections()) {
            for (Paragraph para : (Iterable<Paragraph>) section.getParagraphs()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    para.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
                }
            }
        }
    }
}

