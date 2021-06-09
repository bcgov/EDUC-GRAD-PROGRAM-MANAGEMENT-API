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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@CrossOrigin
@RestController
@RequestMapping(EducGradProgramManagementApiConstants.GRAD_PROGRAM_MANAGEMENT_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Program Management Data.", description = "This API contains endpoints for Program Management Functionalities.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_PROGRAM_SETS_DATA","READ_GRAD_PROGRAM_RULES_DATA","READ_GRAD_PROGRAM_CODE_DATA","READ_GRAD_SPECIAL_CASE_DATA","READ_GRAD_LETTER_GRADE_DATA"})})
public class ProgramManagementController {

    private static Logger logger = LoggerFactory.getLogger(ProgramManagementController.class);

    @Autowired
    ProgramManagementService programManagementService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;
    
    private static final String PROGRAM_CODE="Program Code";
    private static final String PROGRAM_NAME="Program Name";

    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find All Programs", description = "Get All Programs", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK",content = @Content(array = @ArraySchema(schema = @Schema(implementation = GradProgram.class)))), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradProgram>> getAllPrograms() { 
    	logger.debug("getAllPrograms : ");
    	List<GradProgram> programList = programManagementService.getAllProgramList();
    	if(!programList.isEmpty()) {
    		return response.GET(programList,new TypeToken<List<GradProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM)
    @Operation(summary = "Find Specific Program", description = "Get a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK",content = @Content(array = @ArraySchema(schema = @Schema(implementation = GradProgram.class)))), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<GradProgram> getSpecificProgram(@PathVariable String programCode) { 
    	logger.debug("getSpecificProgram : ");
    	GradProgram gradProgram = programManagementService.getSpecificProgram(programCode);
    	if(gradProgram != null) {
    		return response.GET(gradProgram);
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM)
    @Operation(summary = "Create a Program", description = "Create a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<GradProgram>> createGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.debug("createGradPrograms : ");
    	validation.requiredField(gradProgram.getProgramCode(), PROGRAM_CODE);
       	validation.requiredField(gradProgram.getProgramName(), PROGRAM_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.CREATED(programManagementService.createGradProgram(gradProgram));
    }
    
    @PutMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING ,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM)
    @Operation(summary = "Update a Program", description = "Update a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<GradProgram>> updateGradPrograms(@Valid @RequestBody GradProgram gradProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradProgram.getProgramCode(), PROGRAM_CODE);
      	validation.requiredField(gradProgram.getProgramName(), PROGRAM_NAME);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.UPDATED(programManagementService.updateGradProgram(gradProgram));
    }
    
    @DeleteMapping(EducGradProgramManagementApiConstants.DELETE_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_PROGRAM)
    @Operation(summary = "Delete a Program", description = "Delete a Program", tags = { "Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND.")})
    public ResponseEntity<Void> deleteGradPrograms(@Valid @PathVariable String programCode) { 
    	logger.debug("deleteGradPrograms : ");
    	validation.requiredField(programCode, PROGRAM_CODE);
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(programManagementService.deleteGradPrograms(programCode));
    }
    
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Get Program Rules", description = "Get Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradProgramRule>> getAllProgramsRules(
    		@RequestParam(value = "programCode", required = true) String programCode, 
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradProgramRule> programRuleList = programManagementService.getAllProgramRuleList(programCode,requirementType,accessToken);
    	if(!programRuleList.isEmpty()) {
    		return response.GET(programRuleList,new TypeToken<List<GradProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @PostMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.CREATE_GRAD_PROGRAM_RULES)
    @Operation(summary = "Create Program Rules", description = "Create Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradProgramRule>> createGradProgramRules(@Valid @RequestBody GradProgramRule gradProgramRule) { 
    	logger.debug("createGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getProgramCode(), PROGRAM_CODE);
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
    
    @PutMapping(value=EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES,produces= {"application/json"},consumes= {"application/json"})
    @PreAuthorize(PermissionsContants.UPDATE_GRAD_PROGRAM_RULES)
    @Operation(summary = "Update a Program Rules", description = "Update a Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST.")})
    public ResponseEntity<ApiResponseModel<GradProgramRule>> updateGradProgramRules(@Valid @RequestBody GradProgramRule gradProgramRule) { 
    	logger.debug("updateGradProgramRules : ");
    	validation.requiredField(gradProgramRule.getProgramCode(), PROGRAM_CODE);
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
    @Operation(summary = "Delete a Program Rule", description = "Delete a Program Rule", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND.")})
    public ResponseEntity<Void> deleteGradProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programManagementService.deleteGradProgramRules(UUID.fromString(programRuleID)));
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE_MAPPING,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_CASE)
    @Operation(summary = "Find All Special Cases", description = "Get All Special Cases", tags = { "Independent" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradSpecialCase>> getAllSpecialCases() { 
    	logger.debug("getAllSpecialCases : ");
    	List<GradSpecialCase> specialList = programManagementService.getAllSpecialCaseList();
    	if(!specialList.isEmpty()) {
    		return response.GET(specialList,new TypeToken<List<GradSpecialCase>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE,produces= {"application/json"})
    @Operation(summary = "Find a Specific Special Case", description = "Get a Special Cases", tags = { "Independent" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
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
    @Operation(summary = "Find Specific Rule Details", description = "Get a Specific Rule Detail", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<GradRuleDetails> getSpecificRuleDetails(@PathVariable String ruleCode) { 
    	logger.debug("getSpecificRuleDetails : ");
        return programManagementService.getSpecificRuleDetails(ruleCode);
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE_MAPPING,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_LETTER_GRADE)
    @Operation(summary = "Find All Letter Grade", description = "Get All Letter Grades", tags = { "Independent" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public GradLetterGrades getAllLetterGrades() {
    	logger.debug("getAllLetterGrades : ");
        GradLetterGrades gradLetterGrades = new GradLetterGrades();
        gradLetterGrades.setGradLetterGradeList(programManagementService.getAllLetterGradesList());
        return gradLetterGrades;
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE__BY_LETTER_GRADE,produces= {"application/json"})
    @Operation(summary = "Find a Specific Letter Grade", description = "Get a Letter Grade", tags = { "Independent" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    @PreAuthorize(PermissionsContants.READ_GRAD_LETTER_GRADE)
    public GradLetterGrade getSpecificLetterGrade(@PathVariable String letterGrade) { 
    	logger.debug("getSpecificLetterGrade : ");
        return programManagementService.getSpecificLetterGrade(letterGrade);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_REQUIREMENT_BY_REQUIREMENT_TYPE)
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Check for Requirement Type", description = "Check if Requirement Type is valid", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public Boolean getRequirementByRequirementType(@PathVariable String typeCode) { 
    	logger.debug("getRequirementByRequirementType : ");
        return programManagementService.getRequirementByRequirementType(typeCode);
    }  
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING_BY_ID)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find Special Program", description = "Get Special Program By ID", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<GradSpecialProgram> getAllSpecialProgramsByID(@PathVariable String specialProgramID) { 
    	logger.debug("getAllSpecialProgramsByID : ");
        return response.GET(programManagementService.getSpecialProgramByID(UUID.fromString(specialProgramID)));
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING, produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find All Special Programs", description = "Get All Special Programs", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms() { 
    	logger.debug("getAllSpecialPrograms : ");
    	List<GradSpecialProgram> specialProgramList = programManagementService.getAllSpecialProgramList();
    	if(specialProgramList.size() > 0 ) {
    		return response.GET(specialProgramList,new TypeToken<List<GradSpecialProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find All Special Programs by Program", description = "Get All Special Programs by Program Code", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT.")})
    public ResponseEntity<List<GradSpecialProgram>> getAllSpecialPrograms(@PathVariable String programCode) { 
    	logger.debug("getAllSpecialPrograms : ");
    	List<GradSpecialProgram> specialProgramList = programManagementService.getAllSpecialProgramList(programCode);
    	if(specialProgramList.size() > 0 ) {
    		return response.GET(specialProgramList,new TypeToken<List<GradSpecialProgram>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Find a Special Programs by Program and special program", description = "Get a Special Programs by Program Code and special program code", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<GradSpecialProgram> getSpecialPrograms(@PathVariable String programCode,@PathVariable String specialProgramCode) { 
    	logger.debug("getSpecialPrograms : ");
        return response.GET(programManagementService.getSpecialProgram(programCode,specialProgramCode));
    }
    
    @PostMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_PROGRAM_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_SPECIAL_PROGRAM)
    @Operation(summary = "Create Special Program", description = "Create Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> createGradSpecailPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.debug("createGradSpecailPrograms : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), PROGRAM_CODE);
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
    @Operation(summary = "Update Special Program", description = "Update Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradSpecialProgram>> updateGradSpecialPrograms(@Valid @RequestBody GradSpecialProgram gradSpecialProgram) { 
    	logger.info("updateGradProgramsss : ");
    	validation.requiredField(gradSpecialProgram.getProgramCode(), PROGRAM_CODE);
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
    @Operation(summary = "Delete Special Program", description = "Delete Special Program", tags = { "Special Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND")})
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
    @Operation(summary = "Get Special Program Rules", description = "Get Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
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
    @Operation(summary = "Create Special Program Rules", description = "Create Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
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
    @Operation(summary = "Update Special Program Rules", description = "Update Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
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
    @Operation(summary = "Delete Special Program Rule", description = "Delete Special Program Rule", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND")})
    public ResponseEntity<Void> deleteGradSpecialProgramRules(@PathVariable(value = "programRuleID", required = true) String programRuleID) { 
    	logger.debug("deleteGradProgramRules : ");    	
        return response.DELETE(programManagementService.deleteGradSpecailProgramRules(UUID.fromString(programRuleID)));
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_SPECIAL_PROGRAM_RULES_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_CODE)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Find Special Program Rules by Program Code and Special Program Code and Requirement Type", description = "Get Special Program Rules by Program Code and Special Program Code and Requirement Type", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
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
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_SPECIAL_PROGRAM_RULES_BY_PROGRAM_CODE_AND_SPECIAL_PROGRAM_CODE_ONLY)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Find Special Program Rules by Program Code and Special Program Code", description = "Get Special Program Rules by Program Code and Special Program Code", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(
    		@PathVariable(value = "programCode", required = true) String programCode, 
    		@PathVariable(value = "specialProgramCode", required = true) String specialProgramCode) { 
    	logger.debug("get Special Program Rules By Program Code And Special Program Code : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(value=EducGradProgramManagementApiConstants.GET_PROGRAM_RULES,produces= {"application/json"})
    @PreAuthorize(PermissionsContants.READ_GRAD_PROGRAM_RULES)
    @Operation(summary = "Get all Program Rules", description = "Get all Program Rules", tags = { "Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradProgramRule>> getAllProgramsRules() { 
    	logger.debug("get All Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradProgramRule> programRuleList = programManagementService.getAllProgramRulesList(accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_SPECIAL_PROGRAM_RULES)
    @PreAuthorize(PermissionsContants.READ_GRAD_SPECIAL_PROGRAM_RULES)
    @Operation(summary = "Get all Special Program Rules", description = "Get all Special Program Rules", tags = { "Special Program Rules" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<List<GradSpecialProgramRule>> getAllSpecialProgramRules() { 
    	logger.debug("get All Special Program Rules : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
    	List<GradSpecialProgramRule> programRuleList = programManagementService.getAllSpecialProgramRulesList(accessToken);
    	if(programRuleList.size() > 0) {
    		return response.GET(programRuleList,new TypeToken<List<GradSpecialProgramRule>>() {}.getType());
    	}
    	return response.NO_CONTENT();
    }
    
    
}