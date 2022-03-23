package btuguides.service;

import btuguides.models.binding.PartnersBindingModel;
import btuguides.models.entity.Partners;

public interface PartnerService {
    void savePartner(PartnersBindingModel map);

    Partners findById(String id);

    void remove(String id);

    Object findAll();

    Object find();
}
