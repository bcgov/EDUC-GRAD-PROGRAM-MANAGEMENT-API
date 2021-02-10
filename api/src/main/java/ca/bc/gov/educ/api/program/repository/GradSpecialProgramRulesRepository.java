package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;

@Repository
public interface GradSpecialProgramRulesRepository extends JpaRepository<GradSpecialProgramRulesEntity, UUID> {

    List<GradSpecialProgramRulesEntity> findBySpecialProgramID(UUID programCode);

    @Query("select c from GradSpecialProgramRulesEntity c where c.specialProgramID=:specialProgramID and "
    + "(:requirementType is null or c.requirementType=:requirementType)")
	List<GradSpecialProgramRulesEntity> findBySpecialProgramIDAndRequirementType(UUID specialProgramID, String requirementType);

    @Override
    List<GradSpecialProgramRulesEntity> findAllById(Iterable<UUID> iterable);

    Optional<GradSpecialProgramRulesEntity> findByRuleCode(String ruleCode);

    @Query("select c.id from GradSpecialProgramRulesEntity c where c.ruleCode=:ruleCode and c.specialProgramID=:specialProgramID")
	UUID findIdByRuleCode(String ruleCode,UUID specialProgramID);

    @Query("select c from GradSpecialProgramRulesEntity c where c.requirementType=:typeCode")
	List<GradSpecialProgramRulesEntity> existsByRequirementTypeCode(String typeCode);

}
