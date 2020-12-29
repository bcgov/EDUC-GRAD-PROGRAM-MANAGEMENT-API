package ca.bc.gov.educ.api.program.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Immutable
@Entity
@Table(name = "GRAD_LETTER_GRADE")
public class GradLetterGradeEntity extends BaseEntity {
   
	@Id
	@Column(name = "LETTER_GRADE", nullable = false)
    private String letterGrade; 
	
	@Column(name = "GPA_MARK_VALUE", nullable = true)
    private String gpaMarkValue; 
	
	@Column(name = "PASS_FLAG", nullable = true)
    private String passFlag;
}