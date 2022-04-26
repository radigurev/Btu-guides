package btuguides.repository;

import btuguides.models.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses,String> {
    List<Courses> findAllByType(String type);
}
