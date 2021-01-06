package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradProgramTypes extends BaseModel {

	private String code;	
	private String description;	
	
	@Override
	public String toString() {
		return "GradProgramTypes [code=" + code + ", description=" + description + "]";
	}
	
	
}
