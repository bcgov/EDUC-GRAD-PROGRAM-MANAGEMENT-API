package ca.bc.gov.educ.api.program.model.dto;

import java.sql.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradProgramSet {

	private UUID id;	
	private String programSet;
	private String programSetName;	
	private String gradProgramCode;	
	private String createdBy;	
	private Date createdTimestamp;	
	private String updatedBy;	
	private Date updatedTimestamp;
	
	@Override
	public String toString() {
		return "GradProgramSet [id=" + id + ", programSet=" + programSet + ", programSetName=" + programSetName
				+ ", gradProgramCode=" + gradProgramCode + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}		
}
