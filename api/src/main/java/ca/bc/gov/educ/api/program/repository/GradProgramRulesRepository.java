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

    List<GradProgramRulesEntity> findByProgramSetID(UUID programSetID);

    @Query("select c from GradProgramRulesEntity c where c.programSetID=:programSetID and "
    + "(:requirementType is null or c.requirementType=:requirementType)")
	List<GradProgramRulesEntity> findByProgramSetIDAndRequirementType(UUID programSetID, String requirementType);

    @Override
    List<GradProgramRulesEntity> findAllById(Iterable<UUID> iterable);

    Optional<GradProgramRulesEntity> findByRuleCode(String ruleCode);

    @Query("select c.id from GradProgramRulesEntity c where c.ruleCode=:ruleCode")
	UUID findIdByRuleCode(String ruleCode);

}
