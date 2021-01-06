package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramSetEntity;

@Repository
public interface GradProgramSetRepository extends JpaRepository<GradProgramSetEntity, UUID> {

    List<GradProgramSetEntity> findByGradProgramCode(String programCode);

    @Query("select c.id from GradProgramSetEntity c where c.gradProgramCode=:programCode and c.programSet=:programSet")
	UUID findIdByGradProgramCodeAndProgramSet(String programCode, String programSet);
    
    @Query(value="select DISTINCT s.* from grad_program_rules c inner join grad_program_set s on s.ID = c.fk_program_set_id where s.fk_grad_program_code=:programCode and s.program_set=:programSet",nativeQuery=true)
    Optional<GradProgramSetEntity> findIfProgramRulesExists(@Valid String programCode,@Valid String programSet);
}
