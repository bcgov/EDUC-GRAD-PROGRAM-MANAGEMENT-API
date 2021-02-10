package ca.bc.gov.educ.api.program.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramEntity;

@Repository
public interface GradSpecialProgramRepository extends JpaRepository<GradSpecialProgramEntity, UUID> {

    List<GradSpecialProgramEntity> findAll();

    @Query(value="select DISTINCT p.* from grad_special_program_rules c inner join grad_special_program p on p.id = c.FK_GRAD_SPECIAL_PROGRAM_ID where p.id=:specialProgramID",nativeQuery=true)
	Optional<GradSpecialProgramEntity> findIfChildRecordsExists(@Valid UUID specialProgramID);

	Optional<GradSpecialProgramEntity> findByProgramCodeAndSpecialProgramCode(String programCode, String specialProgramCode);

	List<GradSpecialProgramEntity> findByProgramCode(String programCode);

}
