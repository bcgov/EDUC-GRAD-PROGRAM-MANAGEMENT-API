package ca.bc.gov.educ.api.program.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradLetterGrade extends BaseModel {

	private String letterGrade; 
	private String gpaMarkValue; 
	private String passFlag; 
	
	
	@Override
	public String toString() {
		return "GradLetterGrade [letterGrade=" + letterGrade + ", gpaMarkValue=" + gpaMarkValue + ", passFlag="
				+ passFlag + "]";
	}
	
				
}
