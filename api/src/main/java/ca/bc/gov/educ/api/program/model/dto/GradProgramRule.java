package ca.bc.gov.educ.api.program.model.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradProgramRule extends BaseModel {

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
	private String programCode;
	
	@Override
	public String toString() {
		return "GradProgramRule [id=" + id + ", ruleCode=" + ruleCode + ", requirementName=" + requirementName
				+ ", requirementType=" + requirementType + ", requirementTypeDesc=" + requirementTypeDesc
				+ ", requiredCredits=" + requiredCredits + ", notMetDesc=" + notMetDesc + ", requiredLevel="
				+ requiredLevel + ", languageOfInstruction=" + languageOfInstruction + ", requirementDesc="
				+ requirementDesc + ", isActive=" + isActive + ", programCode=" + programCode + "]";
	}
}
