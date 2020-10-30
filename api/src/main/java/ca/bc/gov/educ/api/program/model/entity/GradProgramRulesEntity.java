package ca.bc.gov.educ.api.program.model.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "GRAD_PROGRAM_RULES")
public class GradProgramRulesEntity {
   
	@Id
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "CODE", nullable = false)
    private String ruleCode; 
	
	@Column(name = "REQUIREMENT_NAME", nullable = true)
    private String requirementName;
	
	@Column(name = "REQUIREMENT_TYPE", nullable = true)
    private String requirementType;
	
	@Column(name = "REQUIRED_CREDITS", nullable = true)
    private String requiredCredits;
	
	@Column(name = "NOT_MET_DESC", nullable = true)
    private String notMetDesc;
	
	@Column(name = "REQUIRED_LEVEL", nullable = true)
    private String requiredLevel;
	
	@Column(name = "LANGUAGE_OF_INSTRUCTION", nullable = true)
    private String languageOfInstruction;
	
	@Column(name = "REQUIREMENT_DESC", nullable = true)
    private String requirementDesc;
	
	@Column(name = "IS_ACTIVE", nullable = true)
    private String isActive;
	
	@Column(name = "FK_PROGRAM_SET_ID", nullable = true)
    private UUID programSetID;
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;	
}