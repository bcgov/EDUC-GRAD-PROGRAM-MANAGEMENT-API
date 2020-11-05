package ca.bc.gov.educ.api.program.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradSpecialCaseEntity;

@Repository
public interface GradSpecialCaseRepository extends JpaRepository<GradSpecialCaseEntity, String> {
}
