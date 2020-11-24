package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.bc.gov.educ.api.program.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.program.model.transformer.GradLetterGradeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramRulesTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramSetTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradSpecialCaseTransformer;
import ca.bc.gov.educ.api.program.repository.GradLetterGradeRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramSetRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialCaseRepository;

@Service
public class ProgramManagementService {

    @Autowired
    private GradProgramRepository gradProgramRepository;  

    @Autowired
    private GradProgramTransformer gradProgramTransformer;
    
    @Autowired
    private GradProgramSetRepository gradProgramSetRepository;  
    
    @Autowired
    private GradProgramRulesRepository gradProgramRulesRepository; 

    @Autowired
    private GradProgramSetTransformer gradProgramSetTransformer;
    
    @Autowired
    private GradProgramRulesTransformer gradProgramRulesTransformer;
    
    @Autowired
    private GradSpecialCaseTransformer specialCaseTransformer;
    
    @Autowired
    private GradLetterGradeTransformer gradLetterGradeTransformer;
    
    @Autowired
    private GradSpecialCaseRepository gradSpecialCaseRepository; 
    
    @Autowired
    private GradLetterGradeRepository gradLetterGradeRepository; 

    private static Logger logger = LoggerFactory.getLogger(ProgramManagementService.class);

     /**
     * Get all Programs in Grad Program DTO
     *
     * @return GradProgram 
     * @throws java.lang.Exception
     */
    public List<GradProgram> getAllProgramList() {
        List<GradProgram> programList  = new ArrayList<GradProgram>();
        try {
        	programList = gradProgramTransformer.transformToDTO(gradProgramRepository.findAll());            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return programList;
    }

	public GradProgramSets getAllProgramSetList(String programCode) {

        GradProgramSets gradProgramSets = new GradProgramSets();

        try {
        	gradProgramSets.setGradProgramSetList(gradProgramSetTransformer
                    .transformToDTO(gradProgramSetRepository.findByGradProgramCode(programCode)));
        	logger.debug("Number of Grad Program Sets for " + programCode + " : " + gradProgramSets.getGradProgramSetList().size());
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return gradProgramSets;
	}

	public List<GradProgramRule> getAllProgramRuleList(String programCode, String programSet, String requirementType) {
		UUID programSetID = gradProgramSetRepository.findIdByGradProgramCodeAndProgramSet(programCode,programSet);
		List<GradProgramRule> programRuleList  = new ArrayList<GradProgramRule>();
        try {
        	programRuleList = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByProgramSetIDAndRequirementType(programSetID,requirementType));            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }
        return programRuleList;
	}
	
	 public List<GradSpecialCase> getAllSpecialCaseList() {
	        List<GradSpecialCase> gradSpecialCaseList  = new ArrayList<GradSpecialCase>();
	        try {
	        	gradSpecialCaseList = specialCaseTransformer.transformToDTO(gradSpecialCaseRepository.findAll());            
	        } catch (Exception e) {
	            logger.debug("Exception:" + e);
	        }

	        return gradSpecialCaseList;
	    }

	public GradSpecialCase getSpecificSpecialCase(String specialCode) {
		return specialCaseTransformer.transformToDTO(gradSpecialCaseRepository.findById(specialCode));
	}

	public GradRuleDetails getSpecificRuleDetails(String ruleCode) {
		GradRuleDetails details = new GradRuleDetails();
		GradProgramRule gradProgramRule = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByRuleCode(ruleCode));
		if(gradProgramRule != null) {
			details.setRuleCode(gradProgramRule.getRuleCode());
			details.setRequirementName(gradProgramRule.getRequirementName());
			GradProgramSet gradProgramSet = gradProgramSetTransformer.transformToDTO(gradProgramSetRepository.getOne(gradProgramRule.getProgramSetID()));
			if(gradProgramSet != null) {
				details.setProgramSet(gradProgramSet.getProgramSet());
				details.setProgramCode(gradProgramSet.getGradProgramCode());
			}
		}
		return details;
	}

	public List<GradLetterGrade> getAllLetterGradesList() {
		List<GradLetterGrade> gradLetterGradeList  = new ArrayList<GradLetterGrade>();
        try {
        	gradLetterGradeList = gradLetterGradeTransformer.transformToDTO(gradLetterGradeRepository.findAll());            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return gradLetterGradeList;
	}

	public GradLetterGrade getSpecificLetterGrade(String letterGrade) {
		return gradLetterGradeTransformer.transformToDTO(gradLetterGradeRepository.findById(letterGrade));
	}
}
