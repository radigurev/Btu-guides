package btuguides.service.implementation;

import btuguides.models.entity.Workers;
import btuguides.repository.WorkerRepository;
import btuguides.service.WorkerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkersService implements WorkerService {

   private final WorkerRepository workerRepository;

    public WorkersService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public List<Workers> findAll() {
        return workerRepository.findAll();
    }
}
