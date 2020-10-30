package ca.bc.gov.educ.api.program.model.dto;

import java.sql.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradProgramRules {

	private UUID id;
    private String ruleCode; 
	private String requirementName;
	private String requirementType;
	private String requiredCredits;
	private String notMetDesc;
	private String requiredLevel;
	private String languageOfInstruction;
	private String requirementDesc;
	private String isActive;
	private String programSetID;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradProgramRules [id=" + id + ", ruleCode=" + ruleCode + ", requirementName=" + requirementName
				+ ", requirementType=" + requirementType + ", requiredCredits=" + requiredCredits + ", notMetDesc="
				+ notMetDesc + ", requiredLevel=" + requiredLevel + ", languageOfInstruction=" + languageOfInstruction
				+ ", requirementDesc=" + requirementDesc + ", isActive=" + isActive + ", programSetID=" + programSetID
				+ ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}	
	
	
}
