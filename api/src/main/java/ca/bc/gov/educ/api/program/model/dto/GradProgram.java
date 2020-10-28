package ca.bc.gov.educ.api.program.model.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradProgram {

	private String programCode; 
	private String programName; 
	private String programType;	
	private Date programStartDate;	
	private Date programEndDate;
	private String createdBy;	
	private Date createdTimestamp;
	private String updatedBy;	
	private Date updatedTimestamp;
	
	
	@Override
	public String toString() {
		return "GradProgram [programCode=" + programCode + ", programName=" + programName + ", programType="
				+ programType + ", programStartDate=" + programStartDate + ", programEndDate=" + programEndDate
				+ ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
	
			
}
