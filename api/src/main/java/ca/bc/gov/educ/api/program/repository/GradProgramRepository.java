package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;

@Repository
public interface GradProgramRepository extends JpaRepository<GradProgramEntity, String> {

    List<GradProgramEntity> findAll();

    @Query(value="select DISTINCT p from grad_program_rules c inner join grad_program_set s on s.ID = c.fk_program_set_id inner join grad_program p on p.code = s.fk_grad_program_code where p.code=:programCode",nativeQuery=true)
	Optional<GradProgramEntity> findIfChildRecordsExists(@Valid String programCode);

}
