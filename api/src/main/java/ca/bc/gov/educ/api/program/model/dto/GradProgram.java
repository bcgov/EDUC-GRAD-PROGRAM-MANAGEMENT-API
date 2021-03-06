package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradProgram extends BaseModel {

	private String programCode; 
	private String programName;
	
	@Override
	public String toString() {
		return "GradProgram [programCode=" + programCode + ", programName=" + programName + "]";
	}
	
	
			
}
