package btuguides.repository;

import btuguides.models.entity.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trips,String> {
    List<Trips> findAllByType(String type);
}
