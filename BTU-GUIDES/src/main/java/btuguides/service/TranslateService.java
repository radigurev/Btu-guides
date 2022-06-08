package btuguides.service;

import btuguides.models.binding.TranslateBindingModel;
import btuguides.models.entity.translate;

public interface TranslateService {
    void save(TranslateBindingModel translateBindingModel);

    translate findById(String id);

    void remove(String id);

    Object findAll();

    Object find();
}
