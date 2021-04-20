package ca.bc.gov.educ.api.program.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradSpecialProgramRule extends BaseModel {

	private UUID id;
    private String ruleCode; 
	private String requirementName;
	private String requirementType;
	private String requirementTypeDesc;
	private String requiredCredits;
	private String notMetDesc;
	private String requiredLevel;
	private String languageOfInstruction;
	private String requirementDesc;
	private String isActive;
	private UUID specialProgramID;
	private String programCode;
	private String specialProgramCode;
	private String ruleCategory;
	
	@Override
	public String toString() {
		return "GradSpecialProgramRule [id=" + id + ", ruleCode=" + ruleCode + ", requirementName=" + requirementName
				+ ", requirementType=" + requirementType + ", requirementTypeDesc=" + requirementTypeDesc
				+ ", requiredCredits=" + requiredCredits + ", notMetDesc=" + notMetDesc + ", requiredLevel="
				+ requiredLevel + ", languageOfInstruction=" + languageOfInstruction + ", requirementDesc="
				+ requirementDesc + ", isActive=" + isActive + ", specialProgramID=" + specialProgramID
				+ ", programCode=" + programCode + ", specialProgramCode=" + specialProgramCode + ", ruleCategory="
				+ ruleCategory + "]";
	}	
	
}
