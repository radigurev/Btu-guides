package btuguides.service;

import btuguides.models.binding.TripBindingModel;
import btuguides.models.entity.Trips;

public interface TripService {
    void save(TripBindingModel tripBindingModel);

    Trips findById(String id);

    void remove(String id);

    Object findAll();

    Object find(String type);
}
