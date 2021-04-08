package ca.bc.gov.educ.api.program.model.entity;

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
@Entity
@Table(name = "GRAD_SPECIAL_PROGRAM_RULES")
@EqualsAndHashCode(callSuper=false)
public class GradSpecialProgramRulesEntity  extends BaseEntity {
   
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "ID", nullable = false)
    private UUID id; 
	
	@Column(name = "PROGRAM_RULE_CODE", nullable = false)
    private String ruleCode; 
	
	@Column(name = "REQUIREMENT_NAME", nullable = true)
    private String requirementName;
	
	@Column(name = "FK_GRAD_REQUIREMENT_TYPE_CODE", nullable = true)
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
	
	@Column(name = "FK_GRAD_SPECIAL_PROGRAM_ID", nullable = true)
    private UUID specialProgramID;
	
	@Column(name = "RULE_CATEGORY", nullable = true)
    private String ruleCategory;
}