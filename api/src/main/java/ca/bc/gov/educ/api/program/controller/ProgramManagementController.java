package ca.bc.gov.educ.api.program.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import ca.bc.gov.educ.api.program.model.dto.*;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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
    	List<GradProgram> programList = programManagementService.getAllProgramList();
    	if(programList.size() > 0 ) {
    		return response.GET(programList,new TypeToken<List<GradProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    public ResponseEntity<GradProgram> getSpecificProgram(@PathVariable String programCode) { 
    	logger.debug("getSpecificProgram : ");
    	GradProgram gradProgram = programManagementService.getSpecificProgram(programCode);
    	if(gradProgram != null) {
    		return response.GET(gradProgram);
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradProgram>> createGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradProgram.getProgramCode(), "Program Code");
       	validation.requiredField(gradProgram.getProgramName(), "Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programManagementService.createGradProgram(gradProgram));
    }
    
    @PutMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradProgram>> updateGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradProgram.getProgramCode(), "Program Code");
      	validation.requiredField(gradProgram.getProgramName(), "Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programManagementService.updateGradProgram(gradProgram));
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.DELETE_PROGRAM_MAPPING)
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
    
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    public ResponseEntity<List<GradProgramRule>> getAllProgramsRules(
    		@RequestParam(value = "programCode", required = true) String programCode, 
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradProgramRule> programRuleList = programManagementService.getAllProgramRuleList(programCode,requirementType,accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM_RULES)
    public ResponseEntity<ApiResponseModel<GradProgramRule>> createGradProgramRules(@Valid @RequestBody GradProgramRule gradProgramRule) { 
    	logger.debug("createGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getProgramCode(), "Program Code");
    	validation.requiredField(gradProgramRule.getRequirementType(), "Requirement Type");
    	validation.requiredField(gradProgramRule.getRuleCode(), "Rule Code");
    	validation.requiredField(gradProgramRule.getRequirementName(), "Requirement Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.CREATED(programManagementService.createGradProgramRules(gradProgramRule,accessToken));
    }
    
    @PutMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM_RULES)
    public ResponseEntity<ApiResponseModel<GradProgramRule>> updateGradProgramRules(@Valid @RequestBody GradProgramRule gradProgramRule) { 
    	logger.debug("updateGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getProgramCode(), "Program Code");
    	validation.requiredField(gradProgramRule.getRequirementType(), "Requirement Type");
    	validation.requiredField(gradProgramRule.getRuleCode(), "Rule Code");
    	validation.requiredField(gradProgramRule.getRequirementName(), "Requirement Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.UPDATED(programManagementService.updateGradProgramRules(gradProgramRule,accessToken));
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.DELETE_PROGRAM_RULES_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_PROGRAM_RULES)
    public ResponseEntity<Void> deleteGradProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programManagementService.deleteGradProgramRules(UUID.fromString(programRuleID)));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_CASE)
    public ResponseEntity<List<GradSpecialCase>> getAllSpecialCases() { 
    	logger.debug("getAllSpecialCases : ");
    	List<GradSpecialCase> specialList = programManagementService.getAllSpecialCaseList();
    	if(specialList.size() > 0 ) {
    		return response.GET(specialList,new TypeToken<List<GradSpecialCase>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_CASE)
    public ResponseEntity<GradSpecialCase> getSpecificSpecialCases(@PathVariable String specialCode) { 
    	logger.debug("getSpecificSpecialCases : ");
    	GradSpecialCase gradSpecialCase = programManagementService.getSpecificSpecialCase(specialCode);
    	if(gradSpecialCase != null) {
    		return response.GET(gradSpecialCase) ;
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    public List<GradRuleDetails> getSpecificRuleDetails(@PathVariable String ruleCode) { 
    	logger.debug("getSpecificRuleDetails : ");
        return programManagementService.getSpecificRuleDetails(ruleCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_LETTER_GRADE)
    public GradLetterGrades getAllLetterGrades() {
    	logger.debug("getAllLetterGrades : ");
        GradLetterGrades gradLetterGrades = new GradLetterGrades();
        gradLetterGrades.setGradLetterGradeList(programManagementService.getAllLetterGradesList());
        return gradLetterGrades;
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE__BY_LETTER_GRADE)
    @PreAuthorize(PermissionsContants.READ_GRAD_LETTER_GRADE)
    public GradLetterGrade getSpecificLetterGrade(@PathVariable String letterGrade) { 
    	logger.debug("getSpecificLetterGrade : ");
        return programManagementService.getSpecificLetterGrade(letterGrade);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_REQUIREMENT_BY_REQUIREMENT_TYPE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    public Boolean getRequirementByRequirementType(@PathVariable String typeCode) { 
    	logger.debug("getRequirementByRequirementType : ");
        return programManagementService.getRequirementByRequirementType(typeCode);
    }  
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING_BY_ID)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<GradSpecialProgram> getAllSpecialProgramsByID(@PathVariable String specialProgramID) { 
    	logger.debug("getAllSpecialProgramsByID : ");
        return response.GET(programManagementService.getSpecialProgramByID(UUID.fromString(specialProgramID)));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms() { 
    	logger.debug("getAllSpecialPrograms : ");
        return response.GET(programManagementService.getAllSpecialProgramList(),new TypeToken<List<GradSpecialProgram>>() {
		}.getType());
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms(@PathVariable String programCode) { 
    	logger.debug("getAllSpecialPrograms : ");
        return response.GET(programManagementService.getAllSpecialProgramList(programCode),new TypeToken<List<GradSpecialProgram>>() {
		}.getType());
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<GradSpecialProgram> getSpecialPrograms(@PathVariable String programCode,@PathVariable String specialProgramCode) { 
    	logger.debug("getSpecialPrograms : ");
        return response.GET(programManagementService.getSpecialProgram(programCode,specialProgramCode));
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> createGradSpecailPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), "Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramCode(), "Special Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramName(), "Special Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programManagementService.createGradSpecialProgram(gradSpecialProgram));
    }
    
    @PutMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> updateGradSpecialPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), "Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramCode(), "Special Program Code");
       	validation.requiredField(gradSpecialProgram.getSpecialProgramName(), "Special Program Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programManagementService.updateGradSpecialPrograms(gradSpecialProgram));
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.DELETE_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_SPECIAL_PROGRAM)
    public ResponseEntity<Void> deleteGradSpecialPrograms(@PathVariable(value = "specialProgramID", required = true) String specialProgramID) { 
    	logger.debug("deleteGradPrograms : ");
    	validation.requiredField(specialProgramID, "Special Program ID");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(programManagementService.deleteGradSpecialPrograms(UUID.fromString(specialProgramID)));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    public ResponseEntity<List<GradSpecialProgramRule>> getAllSpecialProgramRules(
    		@RequestParam(value = "specialProgramID", required = true) String specialProgramID, 
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Special Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programManagementService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_SPECIAL_PROGRAM_RULES)
    public ResponseEntity<ApiResponseModel<GradSpecialProgramRule>> createGradSpecialProgramRules(@Valid @RequestBody GradSpecialProgramRule gradSpecialProgramRule) { 
    	logger.debug("createGradSpecialProgramRules : ");
    	validation.requiredField(gradSpecialProgramRule.getSpecialProgramID(), "Special Program ID");
    	validation.requiredField(gradSpecialProgramRule.getRequirementType(), "Requirement Type");
    	validation.requiredField(gradSpecialProgramRule.getRuleCode(), "Rule Code");
    	validation.requiredField(gradSpecialProgramRule.getRequirementName(), "Requirement Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.CREATED(programManagementService.createGradSpecialProgramRules(gradSpecialProgramRule,accessToken));
    }
    
    @PutMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_SPECIAL_PROGRAM_RULES)
    public ResponseEntity<ApiResponseModel<GradSpecialProgramRule>> updateGradProgramRules(@Valid @RequestBody GradSpecialProgramRule gradSpecialProgramRule) { 
    	logger.debug("updateGradProgramRules : ");
    	validation.requiredField(gradSpecialProgramRule.getSpecialProgramID(), "Special Program ID");
    	validation.requiredField(gradSpecialProgramRule.getRequirementType(), "Requirement Type");
    	validation.requiredField(gradSpecialProgramRule.getRuleCode(), "Rule Code");
    	validation.requiredField(gradSpecialProgramRule.getRequirementName(), "Requirement Name");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	return response.UPDATED(programManagementService.updateGradSpecialProgramRules(gradSpecialProgramRule,accessToken));
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.DELETE_SPECIAL_PROGRAM_RULES_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_SPECIAL_PROGRAM_RULES)
    public ResponseEntity<Void> deleteGradSpecialProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programManagementService.deleteGradSpecailProgramRules(UUID.fromString(programRuleID)));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_SPECIAL_PROGRAM_RULES_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_CODE)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    public ResponseEntity<List<GradSpecialProgramRule>> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(
    		@PathVariable(value = "programCode", required = true) String programCode, 
    		@PathVariable(value = "specialProgramCode", required = true) String specialProgramCode,
    		@PathVariable(value = "requirementType", required = true) String requirementType) { 
    	logger.debug("get Special Program Rules By Program Code And Special Program Code : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    
}