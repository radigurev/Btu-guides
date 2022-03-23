package btuguides.web;

import btuguides.models.binding.PartnersBindingModel;
import btuguides.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/edit/site/97979f2c-aa80-11ec-b909-0242ac120002")
public class AdminController {

    private final PartnerService partnerService;
    public AdminController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @ModelAttribute
    public PartnersBindingModel partnersBindingModel() {
        return new PartnersBindingModel();
    }

    @GetMapping("")
    public String adminPage() {
        return "admin-page";
    }

    @GetMapping("/trips")
    public String returnTrips() {
        return "add-trip";
    }

    @GetMapping("/translate/offers")
    public String returnTranslateOffers(){
        return "add-translate-offer";
    }

    @GetMapping("/partners")
    public String returnPartners() {
        return "add-partners";
    }

    @GetMapping("/courses")
    public String returnCourses() {
        return "add-courses";
    }
    @GetMapping("/translators")
    public String returnWorkers() {
        return "add-workers";
    }

    @GetMapping("/table/courses")
    public String returnTableCourses() {
        return "table";
    }

    @GetMapping("/table/partners")
    public String returnTablePartners() {
        return "table";
    }

    @GetMapping("/table/translate")
    public String returnTableTranslate() {
        return "table";
    }

    @GetMapping("/table/trip")
    public String returnTableTrips() {
        return "table";
    }

    @GetMapping("/table/translators")
    public String returnTableTranslators() {
        return "table";
    }


    //POSTMAPPING

    @PostMapping("/partners")
    public String postPartners(PartnersBindingModel partnersBindingModel) {
        partnerService.savePartner(partnersBindingModel);
        return "add-partners";
    }


}
