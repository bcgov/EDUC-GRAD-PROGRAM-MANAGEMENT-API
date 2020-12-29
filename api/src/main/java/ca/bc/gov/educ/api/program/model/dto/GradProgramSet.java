package ca.bc.gov.educ.api.program.model.dto;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class GradProgramSet extends BaseModel {

	private UUID id;	
	private String programSet;
	private String programSetName;	
	private String gradProgramCode;
	private String programSetStartDate;	
	private String programSetEndDate;
	
	@Override
	public String toString() {
		return "GradProgramSet [id=" + id + ", programSet=" + programSet + ", programSetName=" + programSetName
				+ ", gradProgramCode=" + gradProgramCode + "]";
	}		
}
