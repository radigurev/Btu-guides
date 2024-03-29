package btuguides.service.implementation;

import btuguides.models.binding.CoursesBindingModel;
import btuguides.models.entity.Courses;
import btuguides.models.view.TableViewModel;
import btuguides.repository.CourseRepository;
import btuguides.service.CourseService;
import btuguides.service.WorkerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoursesService implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public CoursesService(CourseRepository courseRepository, ModelMapper modelMapper, WorkerService workerService) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CoursesBindingModel coursesBindingModel) {
        System.out.println();
        Courses course= modelMapper.map(coursesBindingModel, Courses.class);
        if(course.getWorkerUrl().isEmpty()) {
            course.setWorkerUrl("localhost:8080/");
        }
        courseRepository.save(course);

    }

    @Override
    public Courses findById(String id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(String id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Object findAll() {
        List<TableViewModel> list=new ArrayList<>();
        courseRepository.findAll().forEach(e -> {
            TableViewModel tb=new TableViewModel();
            tb.setName(e.getName());
            tb.setId(e.getId());
            list.add(tb);
        });
        return list;
    }

    @Override
    public Object find(String type) {
        return courseRepository.findAllByType(type);
    }
}
