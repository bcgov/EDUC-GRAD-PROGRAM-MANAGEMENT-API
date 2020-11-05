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
@Table(name = "GRAD_SPECIAL_CASE")
public class GradSpecialCaseEntity {
   	
	@Id
	@Column(name = "SPECIAL_CASE", nullable = false)
    private String specialCase; 
	
	@Column(name = "PASS_FLAG", nullable = true)
    private String passFlag; 
	
	@Column(name = "CASE_DESC", nullable = true)
    private String description; 	
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;	
}