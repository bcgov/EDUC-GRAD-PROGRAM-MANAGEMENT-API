package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradRuleDetails extends BaseModel {

	private String ruleCode; 
	private String requirementName;
	private String programCode;	
	
	@Override
	public String toString() {
		return "GradRuleDetails [ruleCode=" + ruleCode + ", requirementName=" + requirementName + ", programCode="
				+ programCode + "]";
	}
	
}
