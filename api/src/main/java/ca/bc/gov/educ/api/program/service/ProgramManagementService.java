package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSets;
import ca.bc.gov.educ.api.program.model.dto.GradProgramTypes;
import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramSetEntity;
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
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiConstants;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiUtils;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
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
    
    @Value(EducGradProgramManagementApiConstants.ENDPOINT_PROGRAM_TYPE_BY_CODE_URL)
    private String getProgramTypeByCodeURL;
    
    @Value(EducGradProgramManagementApiConstants.ENDPOINT_REQUIREMENT_TYPE_BY_CODE_URL)
    private String getRequirementTypeByCodeURL;    
    
    @Autowired
    RestTemplate restTemplate;

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

	public List<GradProgramRule> getAllProgramRuleList(String programCode, String programSet, String requirementType,String accessToken) {
		UUID programSetID = gradProgramSetRepository.findIdByGradProgramCodeAndProgramSet(programCode,programSet);
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		List<GradProgramRule> programRuleList  = new ArrayList<GradProgramRule>();
        try {
        	if(StringUtils.isNotBlank(requirementType)) {
        	restTemplate.exchange(String.format(getRequirementTypeByCodeURL,requirementType), HttpMethod.GET,
    				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
        	}
        	programRuleList = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByProgramSetIDAndRequirementType(programSetID,requirementType));   
        	programRuleList.forEach(pR-> {
        		GradRequirementTypes reqType = restTemplate.exchange(String.format(getRequirementTypeByCodeURL,pR.getRequirementType()), HttpMethod.GET,
        				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
        		pR.setRequirementTypeDesc(reqType.getDescription());
        	});
        } catch (Exception e) {
            logger.debug("Exception:" + e);
            validation.addErrorAndStop(String.format("Requirement Type [%s] is not Valid",requirementType));
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

	public GradProgram createGradProgram(GradProgram gradProgram,String accessToken) {
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		GradProgramEntity toBeSavedObject = gradProgramTransformer.transformToEntity(gradProgram);
		Optional<GradProgramEntity> existingObjectCheck = gradProgramRepository.findById(gradProgram.getProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Program Code [%s] already exists",gradProgram.getProgramCode()));
			return gradProgram;
			
		}else {
			try {
			GradProgramTypes programTypes = restTemplate.exchange(String.format(getProgramTypeByCodeURL,gradProgram.getProgramType()), HttpMethod.GET,
    				new HttpEntity<>(httpHeaders), GradProgramTypes.class).getBody();
    		if(programTypes != null) { 
    			return gradProgramTransformer.transformToDTO(gradProgramRepository.save(toBeSavedObject));    			
    		}else {
    			validation.addErrorAndStop(String.format("Program Type [%s] is not Valid",gradProgram.getProgramType()));
    			return gradProgram;
    		}	
			}catch(Exception c) {
				validation.addErrorAndStop(String.format("Program Type [%s] is not Valid",gradProgram.getProgramType()));
				throw new GradBusinessRuleException();
			}
		}		
	}
	
	@Transactional
	public GradProgram updateGradProgram(GradProgram gradProgram,String accessToken) {
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		Optional<GradProgramEntity> gradProgramOptional = gradProgramRepository.findById(gradProgram.getProgramCode());
		GradProgramEntity sourceObject = gradProgramTransformer.transformToEntity(gradProgram);
		if(gradProgramOptional.isPresent()) {
			try {
				GradProgramEntity gradEnity = gradProgramOptional.get();			
				BeanUtils.copyProperties(sourceObject,gradEnity,"createdBy","createdTimestamp");
				GradProgramTypes programTypes = restTemplate.exchange(String.format(getProgramTypeByCodeURL,gradProgram.getProgramType()), HttpMethod.GET,
	    				new HttpEntity<>(httpHeaders), GradProgramTypes.class).getBody();
	    		if(programTypes != null) {
	    			return gradProgramTransformer.transformToDTO(gradProgramRepository.save(gradEnity));
	    		}else {
	    			validation.addErrorAndStop(String.format("Program Type [%s] is not Valid",gradProgram.getProgramType()));
	    			return gradProgram;
	    		}
			}catch(Exception c) {
				validation.addErrorAndStop(String.format("Program Type [%s] is not Valid",gradProgram.getProgramType()));
				throw new GradBusinessRuleException();
			}
		}else {
			validation.addErrorAndStop(String.format("Program Code [%s] does not exists",gradProgram.getProgramCode()));
			return gradProgram;
		}
	}

	public int deleteGradPrograms(@Valid String programCode) {
		Optional<GradProgramEntity> gradProgramOptional = gradProgramRepository.findIfProgramSetExists(programCode);
		if(gradProgramOptional.isPresent()) {
			validation.addErrorAndStop(String.format("This Program [%s] cannot be deleted as it has program sets and rules data associated with it.",gradProgramOptional.get().getProgramCode()));
			return 0;
		}else {
			gradProgramRepository.deleteById(programCode);
			return 1;
		}		
	}

	public GradProgramSet createGradProgramSet(@Valid GradProgramSet gradProgramSet) {
		GradProgramSetEntity toBeSavedObject = gradProgramSetTransformer.transformToEntity(gradProgramSet);
		UUID existingObjectCheck = gradProgramSetRepository.findIdByGradProgramCodeAndProgramSet(gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet());
		if(existingObjectCheck != null) {
			validation.addErrorAndStop(String.format("Combination of Program Code [%s] and Program Set [%s] already exists",gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet()));
			return gradProgramSet;			
		}else {			
			if(gradProgramRepository.existsById(gradProgramSet.getGradProgramCode()) && gradProgramRepository.existsById(gradProgramSet.getProgramSet())) { 
    			return gradProgramSetTransformer.transformToDTO(gradProgramSetRepository.save(toBeSavedObject));    			
    		}else {
    			validation.addErrorAndStop(String.format("Either Grad Program [%s] or Program Set [%s] is not Valid",gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet()));
    			return gradProgramSet;
    		}	
			
		}
	}

	public GradProgramSet updateGradProgramSet(@Valid GradProgramSet gradProgramSet) {
		GradProgramSetEntity sourceObject = gradProgramSetTransformer.transformToEntity(gradProgramSet);
		Optional<GradProgramSetEntity> gradProgramSetOptional = gradProgramSetRepository.findById(gradProgramSet.getId());
		if(gradProgramSetOptional.isPresent()) {
			GradProgramSetEntity gradSetEnity = gradProgramSetOptional.get();	
			if(checkIfProgramSetandCodeChanged(gradSetEnity,sourceObject)) {
				BeanUtils.copyProperties(sourceObject,gradSetEnity,"createdBy","createdTimestamp");
				UUID existingObjectCheck = gradProgramSetRepository.findIdByGradProgramCodeAndProgramSet(gradSetEnity.getGradProgramCode(),gradSetEnity.getProgramSet());
				if(existingObjectCheck != null) {
					validation.addErrorAndStop(String.format("Combination of Program Code [%s] and Program Set [%s] already exists",gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet()));
					return gradProgramSet;			
				}else {			
					if(gradProgramRepository.existsById(gradProgramSet.getGradProgramCode()) && gradProgramRepository.existsById(gradProgramSet.getProgramSet())) { 
		    			return gradProgramSetTransformer.transformToDTO(gradProgramSetRepository.save(gradSetEnity));    			
		    		}else {
		    			validation.addErrorAndStop(String.format("Either Grad Program [%s] or Program Set [%s] is not Valid",gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet()));
		    			return gradProgramSet;
		    		}				
				}
			}else {
				if(gradProgramRepository.existsById(gradProgramSet.getGradProgramCode()) && gradProgramRepository.existsById(gradProgramSet.getProgramSet())) { 
					BeanUtils.copyProperties(sourceObject,gradSetEnity,"createdBy","createdTimestamp");
					return gradProgramSetTransformer.transformToDTO(gradProgramSetRepository.save(gradSetEnity));    			
	    		}else {
	    			validation.addErrorAndStop(String.format("Either Grad Program [%s] or Program Set [%s] is not Valid",gradProgramSet.getGradProgramCode(),gradProgramSet.getProgramSet()));
	    			return gradProgramSet;
	    		}
			}
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradProgramSet;
		}
	}
	
	private boolean checkIfProgramSetandCodeChanged(GradProgramSetEntity gradSetEnity, GradProgramSetEntity sourceObject) {
		if(!sourceObject.getProgramSet().equals(gradSetEnity.getProgramSet()) || !sourceObject.getGradProgramCode().equals(gradSetEnity.getGradProgramCode())) {
			return true;
		}
		return false;
		
	}

	public int deleteGradProgramSets(UUID programSetID, @Valid String programCode,@Valid String programSet) {
		Optional<GradProgramSetEntity> gradProgramSetOptional = gradProgramSetRepository.findIfProgramRulesExists(programCode, programSet);
		if(gradProgramSetOptional.isPresent()) {
			validation.addErrorAndStop(String.format("This Program Set [%s] cannot be deleted as it has rules data associated with it.",gradProgramSetOptional.get().getProgramSet()));
			return 0;
		}else {
			gradProgramSetRepository.deleteById(programSetID);
			return 1;
		}		
	}
}
