package btuguides.service.implementation;

import btuguides.models.binding.WorkerBindingModel;
import btuguides.models.entity.Workers;
import btuguides.models.view.TableViewModel;
import btuguides.repository.WorkerRepository;
import btuguides.service.WorkerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WorkersService implements WorkerService {

   private final WorkerRepository workerRepository;
    private final ModelMapper modelMapper;
    public WorkersService(WorkerRepository workerRepository, ModelMapper modelMapper) {
        this.workerRepository = workerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> findAll() {
        List<String> list=new ArrayList<>();
        workerRepository.findAll().forEach(workers -> {
            list.add(String.format("%s %s",workers.getFirstName(),workers.getLastName()));
        });

        return list;
    }

    @Override
    public void save(WorkerBindingModel map) {
        if (Objects.equals(map.getUrl(), "")){
            map.setUrl("https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png");
        }
        workerRepository.save(modelMapper.map(map, Workers.class));
    }

    @Override
    public Workers findByNames(String name, String family) {
        return workerRepository.findByFirstNameAndLastName(name, family).orElse(null);
    }

    @Override
    public void remove(String id) {
        workerRepository.deleteById(id);
    }

    @Override
    public Object findAllViews() {
        List<Workers> list=workerRepository.findAll();
        List<TableViewModel> table = new ArrayList<>();
        list.forEach(e -> {
            TableViewModel tb=new TableViewModel();
            tb.setId(e.getId());
            tb.setName(String.format("%s %s",e.getFirstName(),e.getLastName()));
            table.add(tb);
        });
        return table;
    }

    @Override
    public Object find() {
        return workerRepository.findAll();
    }
}
