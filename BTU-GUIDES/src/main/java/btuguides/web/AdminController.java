package btuguides.web;

import btuguides.models.binding.*;
import btuguides.models.entity.*;
import btuguides.repository.WorkerRepository;
import btuguides.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/edit/site/97979f2c-aa80-11ec-b909-0242ac120002")
public class AdminController {

    private final PartnerService partnerService;
    private final WorkerService workerService;
    private final TripService tripService;
    private final TranslateService translateService;
    private final CourseService courseService;
    private final WorkerRepository workerRepository;
    public AdminController(PartnerService partnerService, WorkerService workerService, TripService tripService, TranslateService translateService, CourseService courseService, WorkerRepository workerRepository) {
        this.partnerService = partnerService;
        this.workerService = workerService;
        this.tripService = tripService;
        this.translateService = translateService;
        this.courseService = courseService;
        this.workerRepository = workerRepository;
    }

    @ModelAttribute
    public PartnersBindingModel partnersBindingModel() {
        return new PartnersBindingModel();
    }

    @ModelAttribute
    public TripBindingModel tripBindingModel() {
        return new TripBindingModel();
    }

    @ModelAttribute
    public WorkerBindingModel workerBindingModel() {
        return new WorkerBindingModel();
    }

    @ModelAttribute
    public TranslateBindingModel translateBindingModel() {
        return new TranslateBindingModel();
    }

    @ModelAttribute
    public CoursesBindingModel coursesBindingModel() {
        return new CoursesBindingModel();
    }

    @GetMapping("")
    public String adminPage() {
        return "admin-page";
    }

    @GetMapping("/trips")
    public String returnTrips(Model model) {
        model.addAttribute("workers", workerService.findAll());
        return "add-trip";
    }

    @GetMapping("/translate/offers")
    public String returnTranslateOffers() {
        return "add-translate-offer";
    }

    @GetMapping("/partners")
    public String returnPartners() {
        return "add-partners";
    }

    @GetMapping("/courses")
    public String returnCourses(Model model) {
        model.addAttribute("workers",workerService.findAll());
        return "add-courses";
    }

    @GetMapping("/translators")
    public String returnWorkers() {
        return "add-workers";
    }

    @GetMapping("/table/courses")
    public String returnTableCourses(Model model) {
        model.addAttribute("rows",courseService.findAll());
        return "table";
    }

    @GetMapping("/table/partners")
    public String returnTablePartners(Model model) {
        model.addAttribute("rows",partnerService.findAll());
        return "table";
    }

    @GetMapping("/table/translate")
    public String returnTableTranslate(Model model) {
        model.addAttribute("rows",translateService.findAll());
        return "table";
    }

    @GetMapping("/table/trip")
    public String returnTableTrips(Model model) {
        model.addAttribute("rows",tripService.findAll());
        return "table";
    }

    @GetMapping("/table/translators")
    public String returnTableTranslators(Model model) {
        model.addAttribute("rows",workerService.findAllViews());
        return "table";
    }


    //POSTMAPPING

    @PostMapping("/partners")
    public String postPartners(PartnersBindingModel partnersBindingModel) {
        partnerService.savePartner(partnersBindingModel);
        return "redirect:partners";
    }

    @PostMapping("/translators")
    public String postTranslators(WorkerBindingModel workerBindingModel) {
        workerService.save(workerBindingModel);
        return "redirect:translators";
    }

    @PostMapping("/trips")
    public String postTrip(TripBindingModel tripBindingModel) {
        tripService.save(tripBindingModel);
        return "redirect:trips";
    }

    @PostMapping("/translate/offers")
    public String postTranslate(TranslateBindingModel translateBindingModel) {
        translateService.save(translateBindingModel);
        return "redirect:offers";
    }

    @PostMapping("/courses")
    public String postCourses(CoursesBindingModel coursesBindingModel) {
        courseService.save(coursesBindingModel);
        return "redirect:courses";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable String id) {
        String section="";
        Courses courses=courseService.findById(id);
        Partners partners=partnerService.findById(id);
        translate tran=translateService.findById(id);
        Trips trip=tripService.findById(id);
        Workers worker=workerRepository.findById(id).orElse(null);

        if(courses!=null) {
            section="courses";
            courseService.remove(id);
        }else if (partners!=null) {
            section="partners";
            partnerService.remove(id);
        }else if (tran!=null) {
            section="translate";
            translateService.remove(id);
        }else if (trip!=null) {
            section="trip";
            tripService.remove(id);
        }else {
            section="translators";
            workerService.remove(id);
        }



        return String.format("redirect:/admin/edit/site/97979f2c-aa80-11ec-b909-0242ac120002/table/%s",section);
    }
}
