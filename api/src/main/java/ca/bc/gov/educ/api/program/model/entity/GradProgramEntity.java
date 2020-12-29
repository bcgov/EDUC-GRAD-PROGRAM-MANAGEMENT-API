package ca.bc.gov.educ.api.program.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Immutable
@Entity
@Table(name = "GRAD_PROGRAM")
public class GradProgramEntity extends BaseEntity {
   
	@Id
	@Column(name = "CODE", nullable = false)
    private String programCode; 
	
	@Column(name = "NAME", nullable = true)
    private String programName; 

	@Column(name = "FK_GRAD_PROGRAM_TYPE_CODE", nullable = true)
    private String programType;		
}