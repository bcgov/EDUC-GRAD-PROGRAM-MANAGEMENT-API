package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSets;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
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
import ca.bc.gov.educ.api.program.util.GradValidation;

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
    
    @Autowired
	GradValidation validation;

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

	public GradProgram createGradProgram(GradProgram gradProgram) {
		GradProgramEntity toBeSavedObject = gradProgramTransformer.transformToEntity(gradProgram);
		Optional<GradProgramEntity> existingObjectCheck = gradProgramRepository.findById(gradProgram.getProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Program Code [%s] already exists",gradProgram.getProgramCode()));
			return gradProgram;
			
		}else {
			return gradProgramTransformer.transformToDTO(gradProgramRepository.save(toBeSavedObject));			
		}		
	}
	
	public GradProgram updateGradProgram(GradProgram gradProgram) {
		Optional<GradProgramEntity> gradProgramOptional = gradProgramRepository.findById(gradProgram.getProgramCode());
		GradProgramEntity sourceObject = gradProgramTransformer.transformToEntity(gradProgram);
		if(gradProgramOptional.isPresent()) {
			GradProgramEntity gradEnity = gradProgramOptional.get();			
			BeanUtils.copyProperties(sourceObject,gradEnity,"createdBy","createdTimestamp");
			return gradProgramTransformer.transformToDTO(gradProgramRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Program Code [%s] does not exists",gradProgram.getProgramCode()));
			return gradProgram;
		}
	}

	public int deleteGradPrograms(@Valid String programCode) {
		Optional<GradProgramEntity> gradProgramOptional = gradProgramRepository.findIfChildRecordsExists(programCode);
		if(gradProgramOptional.isPresent()) {
			validation.addErrorAndStop(String.format("This Program [%s] cannot be deleted as it has program sets and rules data associated with it.",gradProgramOptional.get().getProgramCode()));
			return 0;
		}else {
			gradProgramRepository.deleteById(programCode);
			return 1;
		}		
	}
}
