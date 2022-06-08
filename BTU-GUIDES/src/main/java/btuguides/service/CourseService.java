package btuguides.service;

import btuguides.models.binding.CoursesBindingModel;
import btuguides.models.entity.Courses;

public interface CourseService {
    void save(CoursesBindingModel coursesBindingModel);

    Courses findById(String id);

    void remove(String id);

    Object findAll();

    Object find(String type);
}
