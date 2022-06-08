package btuguides.repository;

import btuguides.models.entity.Partners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partners,String> {
        List<Partners> findAllByType(String type);
}
