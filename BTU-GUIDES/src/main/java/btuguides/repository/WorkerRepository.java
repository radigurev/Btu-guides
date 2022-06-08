package btuguides.repository;

import btuguides.models.entity.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Workers,String> {

    Optional<Workers> findByFirstNameAndLastName(String fName,String lName);
}
