package btuguides.service.implementation;

import btuguides.models.binding.TripBindingModel;
import btuguides.models.entity.Trips;
import btuguides.models.view.TableViewModel;
import btuguides.repository.TripRepository;
import btuguides.service.TripService;
import btuguides.service.WorkerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripsService implements TripService {
    private final TripRepository tripRepository;
    private final WorkerService workerService;
    private final ModelMapper modelMapper;
    public TripsService(TripRepository tripRepository, WorkerService workerService, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.workerService = workerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void save(TripBindingModel tripBindingModel) {
        System.out.println();
        Trips trip=modelMapper.map(tripBindingModel,Trips.class);
        String[] names=tripBindingModel.getWorker().split(" ");
        trip.setWorker(workerService.findByNames(names[0],names[1]));
        tripRepository.save(trip);
    }

    @Override
    public Trips findById(String id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(String id) {
        tripRepository.deleteById(id);
    }

    @Override
    public Object findAll() {
        List<TableViewModel> list=new ArrayList<>();
        tripRepository.findAll().forEach(e -> {
            TableViewModel tb=new TableViewModel();
            tb.setName(e.getTitle());
            tb.setId(e.getId());
            list.add(tb);
        });
        return list;
    }

    @Override
    public Object find(String type) {
        return tripRepository.findAllByType(type);
    }
}

