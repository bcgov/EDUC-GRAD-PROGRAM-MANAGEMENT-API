package ca.bc.gov.educ.api.program.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "GRAD_PROGRAM")
public class GradProgramEntity extends BaseEntity {
   
	@Id
	@Column(name = "PROGRAM_CODE", nullable = false)
    private String programCode; 
	
	@Column(name = "PROGRAM_NAME", nullable = true)
    private String programName; 

}