package ca.bc.gov.educ.api.program.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRules;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.service.ProgramManagementService;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiConstants;

@CrossOrigin
@RestController
@RequestMapping(EducGradProgramManagementApiConstants.GRAD_PROGRAM_MANAGEMENT_API_ROOT_MAPPING)
public class ProgramManagementController {

    private static Logger logger = LoggerFactory.getLogger(ProgramManagementController.class);

    @Autowired
    ProgramManagementService programManagementService;

    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_MAPPING)
    public List<GradProgram> getAllPrograms() { 
    	logger.debug("getAllPrograms : ");
        return programManagementService.getAllProgramList();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_SETS_BY_PROGRAM_CODE)
    public List<GradProgramSet> getAllPrograms(@PathVariable String programCode) { 
    	logger.debug("get All Program Sets : ");
        return programManagementService.getAllProgramSetList(programCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_PROGRAM_RULES)
    public List<GradProgramRules> getAllProgramsRules(
    		@RequestParam(value = "programCode", required = true) String programCode, 
            @RequestParam(value = "programSet", required = true) String programSet,
            @RequestParam(value = "requirementType", required = false) String requirementType) { 
    	logger.debug("get All Program Rules : ");
        return programManagementService.getAllProgramRuleList(programCode,programSet,requirementType);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE_MAPPING)
    public List<GradSpecialCase> getAllSpecialCases() { 
    	logger.debug("getAllSpecialCases : ");
        return programManagementService.getAllSpecialCaseList();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE)
    public GradSpecialCase getSpecificSpecialCases(@PathVariable String specialCode) { 
    	logger.debug("getSpecificSpecialCases : ");
        return programManagementService.getSpecificSpecialCase(specialCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE)
    public GradRuleDetails getSpecificRuleDetails(@PathVariable String ruleCode) { 
    	logger.debug("getSpecificRuleDetails : ");
        return programManagementService.getSpecificRuleDetails(ruleCode);
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE_MAPPING)
    public List<GradLetterGrade> getAllLetterGrades() { 
    	logger.debug("getAllLetterGrades : ");
        return programManagementService.getAllLetterGradesList();
    }
    
    @GetMapping(EducGradProgramManagementApiConstants.GET_ALL_LETTER_GRADE__BY_LETTER_GRADE)
    public GradLetterGrade getSpecificLetterGrade(@PathVariable String letterGrade) { 
    	logger.debug("getSpecificLetterGrade : ");
        return programManagementService.getSpecificLetterGrade(letterGrade);
    }
}
