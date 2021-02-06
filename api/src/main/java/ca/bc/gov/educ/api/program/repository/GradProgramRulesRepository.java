package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;

@Repository
public interface GradProgramRulesRepository extends JpaRepository<GradProgramRulesEntity, UUID> {

    List<GradProgramRulesEntity> findByProgramCode(String programCode);

    @Query("select c from GradProgramRulesEntity c where c.programCode=:programCode and "
    + "(:requirementType is null or c.requirementType=:requirementType)")
	List<GradProgramRulesEntity> findByProgramCodeAndRequirementType(String programCode, String requirementType);

    @Override
    List<GradProgramRulesEntity> findAllById(Iterable<UUID> iterable);

    Optional<GradProgramRulesEntity> findByRuleCode(String ruleCode);

    @Query("select c.id from GradProgramRulesEntity c where c.ruleCode=:ruleCode and c.programCode=:programCode")
	UUID findIdByRuleCode(String ruleCode,String programCode);

    @Query("select c from GradProgramRulesEntity c where c.requirementType=:typeCode")
	List<GradProgramRulesEntity> existsByRequirementTypeCode(String typeCode);

}
