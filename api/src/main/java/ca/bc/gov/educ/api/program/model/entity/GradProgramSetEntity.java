package ca.bc.gov.educ.api.program.model.entity;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "GRAD_PROGRAM_SET")
public class GradProgramSetEntity extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "PROGRAM_SET", nullable = true)
    private String programSet; 

	@Column(name = "NAME", nullable = true)
    private String programSetName; 
	
	@Column(name = "FK_GRAD_PROGRAM_CODE", nullable = false)
	private String gradProgramCode; 
	
	@Column(name = "START_DT", nullable = true)
    private Date programSetStartDate; 
	
	@Column(name = "END_DT", nullable = true)
    private Date programSetEndDate;
}