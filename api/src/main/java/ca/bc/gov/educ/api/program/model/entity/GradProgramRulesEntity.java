package ca.bc.gov.educ.api.program.model.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Immutable
@Entity
@Table(name = "GRAD_PROGRAM_RULES")
@EqualsAndHashCode(callSuper=false)
public class GradProgramRulesEntity  extends BaseEntity {
   
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
	
	@Column(name = "ACTIVE_DT", nullable = true)
    private Date activeDate;
}