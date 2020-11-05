package ca.bc.gov.educ.api.program.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradLetterGrade {

	private String letterGrade; 
	private String gpaMarkValue; 
	private String passFlag; 
	private String createdBy;	
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradLetterGrade [letterGrade=" + letterGrade + ", gpaMarkValue=" + gpaMarkValue + ", passFlag="
				+ passFlag + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy="
				+ updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
				
}
