package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradRuleDetails {

	private String ruleCode; 
	private String requirementName;
	private String programCode;
	private String programSet;
	
	@Override
	public String toString() {
		return "GradRuleDetails [ruleCode=" + ruleCode + ", requirementName=" + requirementName + ", programCode="
				+ programCode + ", programSet=" + programSet + "]";
	}
	
}
