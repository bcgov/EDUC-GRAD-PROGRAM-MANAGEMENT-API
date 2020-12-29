package ca.bc.gov.educ.api.program.controller;

import java.util.List;

import javax.validation.Valid;

import ca.bc.gov.educ.api.program.model.dto.*;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.program.service.ProgramManagementService;
import ca.bc.gov.educ.api.program.util.ApiResponseModel;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiConstants;
import ca.bc.gov.educ.api.program.util.GradValidation;
import ca.bc.gov.educ.api.program.util.PermissionsContants;
import ca.bc.gov.educ.api.program.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin
@RestController
@RequestMapping(EducGradProgramManagementApiConstants.GRAD_PROGRAM_MANAGEMENT_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Program Management Data.", description = "This Read API is for Reading Program Management Tables.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_PROGRAM_SETS_DATA","READ_GRAD_PROGRAM_RULES_DATA","READ_GRAD_PROGRAM_CODE_DATA","READ_GRAD_SPECIAL_CASE_DATA","READ_GRAD_LETTER_GRADE_DATA"})})
public class ProgramManagementController {

    private static Logger logger = LoggerFactory.getLogger(ProgramManagementController.class);

    @Autowired
    ProgramManagementService programManagementService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;

    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    public ResponseEntity<List<GradProgram>> getAllPrograms() { 
    	logger.debug("getAllPrograms : ");
        return response.GET(programManagementService.getAllProgramList(),new TypeToken<List<GradProgram>>() {
		}.getType());
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradProgram>> createGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradProgram.getProgramCode(), "Program Code");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programManagementService.createGradProgram(gradProgram));
    }
    
    @PutMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradProgram>> updateGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradProgram.getProgramCode(), "Program Code");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programManagementService.updateGradProgram(gradProgram),GradProgram.class);
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_PROGRAM)
    public ResponseEntity<Void> deleteGradPrograms(@Valid @PathVariable String programCode) { 
    	logger.debug("deleteGradPrograms : ");
    	validation.requiredField(programCode, "Program Code");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(programManagementService.deleteGradPrograms(programCode));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_SETS_BY_PROGRAM_CODE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_SET)
    public GradProgramSets getAllPrograms(@PathVariable String programCode) {
    	logger.debug("get All Program Sets : ");
        return programManagementService.getAllProgramSetList(programCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_PROGRAM_RULES_DATA')")
    public List<GradProgramRule> getAllProgramsRules(
    		@RequestParam(value = "programCode", required = true) String programCode, 
            @RequestParam(value = "programSet", required = true) String programSet,
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Program Rules : ");
        return programManagementService.getAllProgramRuleList(programCode,programSet,requirementType);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_SPECIAL_CASE_DATA')")
    public List<GradSpecialCase> getAllSpecialCases() { 
    	logger.debug("getAllSpecialCases : ");
        return programManagementService.getAllSpecialCaseList();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_SPECIAL_CASE_DATA')")
    public GradSpecialCase getSpecificSpecialCases(@PathVariable String specialCode) { 
    	logger.debug("getSpecificSpecialCases : ");
        return programManagementService.getSpecificSpecialCase(specialCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_PROGRAM_RULES_DATA')")
    public GradRuleDetails getSpecificRuleDetails(@PathVariable String ruleCode) { 
    	logger.debug("getSpecificRuleDetails : ");
        return programManagementService.getSpecificRuleDetails(ruleCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE_MAPPING)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_LETTER_GRADE_DATA')")
    public GradLetterGrades getAllLetterGrades() {
    	logger.debug("getAllLetterGrades : ");
        GradLetterGrades gradLetterGrades = new GradLetterGrades();
        gradLetterGrades.setGradLetterGradeList(programManagementService.getAllLetterGradesList());
        return gradLetterGrades;
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE__BY_LETTER_GRADE)
    @PreAuthorize("#oauth2.hasScope('READ_GRAD_LETTER_GRADE_DATA')")
    public GradLetterGrade getSpecificLetterGrade(@PathVariable String letterGrade) { 
    	logger.debug("getSpecificLetterGrade : ");
        return programManagementService.getSpecificLetterGrade(letterGrade);
    }
}
