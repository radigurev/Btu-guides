package btuguides.service;

import btuguides.models.binding.WorkerBindingModel;
import btuguides.models.entity.Workers;

import java.util.List;

public interface WorkerService {
    List<Workers> findAll();

    void save(WorkerBindingModel workerBindingModel);

    Workers findByNames(String name, String family);

    void remove(String id);

    Object findAllViews();

    Object find();

    Workers findById(String worker);
}
