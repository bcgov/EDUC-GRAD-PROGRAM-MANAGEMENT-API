package ca.bc.gov.educ.api.program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;

@Repository
public interface GradProgramRepository extends JpaRepository<GradProgramEntity, String> {

    List<GradProgramEntity> findAll();

}
