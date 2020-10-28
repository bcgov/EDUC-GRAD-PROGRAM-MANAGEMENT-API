package ca.bc.gov.educ.api.program.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "GRAD_PROGRAM")
public class GradProgramEntity {
   
	@Id
	@Column(name = "CODE", nullable = false)
    private String programCode; 
	
	@Column(name = "NAME", nullable = true)
    private String programName; 

	@Column(name = "TYPE", nullable = true)
    private String programType; 
	
	@Column(name = "START_DT", nullable = true)
    private Date programStartDate; 
	
	@Column(name = "END_DT", nullable = true)
    private Date programEndDate;
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;	
}