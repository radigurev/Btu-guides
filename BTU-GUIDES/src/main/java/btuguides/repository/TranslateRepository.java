package btuguides.repository;

import btuguides.models.entity.translate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslateRepository extends JpaRepository<translate,String> {
}
