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
@Table(name = "GRAD_SPECIAL_CASE")
public class GradSpecialCaseEntity extends BaseEntity {
   	
	@Id
	@Column(name = "SPECIAL_CASE", nullable = false)
    private String specialCase; 
	
	@Column(name = "PASS_FLAG", nullable = true)
    private String passFlag; 
	
	@Column(name = "CASE_DESC", nullable = true)
    private String description;
}