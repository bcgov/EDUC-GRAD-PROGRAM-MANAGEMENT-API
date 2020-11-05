package ca.bc.gov.educ.api.program.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Immutable
@Entity
@Table(name = "GRAD_LETTER_GRADE")
public class GradLetterGradeEntity {
   
	@Id
	@Column(name = "LETTER_GRADE", nullable = false)
    private String programCode; 
	
	@Column(name = "GPA_MARK_VALUE", nullable = true)
    private String gpaMarkValue; 
	
	@Column(name = "PASS_FLAG", nullable = true)
    private String passFlag;	
	
	@Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
	
	@Column(name = "CREATED_TIMESTAMP", nullable = true)
    private Date createdTimestamp;
	
	@Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
	
	@Column(name = "UPDATED_TIMESTAMP", nullable = true)
    private Date updatedTimestamp;	
}