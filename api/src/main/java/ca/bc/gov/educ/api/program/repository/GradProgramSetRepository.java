package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramSetEntity;

@Repository
public interface GradProgramSetRepository extends JpaRepository<GradProgramSetEntity, UUID> {

    List<GradProgramSetEntity> findByGradProgramCode(String programCode);

}
