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
import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.transformer.GradLetterGradeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramRulesTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradSpecialCaseTransformer;
import ca.bc.gov.educ.api.program.repository.GradLetterGradeRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
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
    private GradProgramRulesRepository gradProgramRulesRepository;     
    
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
    
	public List<GradProgramRule> getAllProgramRuleList(String programCode, String requirementType,String accessToken) {
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		List<GradProgramRule> programRuleList  = new ArrayList<GradProgramRule>();
        try {
        	if(StringUtils.isNotBlank(requirementType)) {
        	restTemplate.exchange(String.format(getRequirementTypeByCodeURL,requirementType), HttpMethod.GET,
    				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
        	}
        	programRuleList = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByProgramCodeAndRequirementType(programCode,requirementType));   
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
			details.setProgramCode(gradProgramRule.getProgramCode());
			
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
	
	@Transactional
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
			validation.addErrorAndStop(String.format("This Program [%s] cannot be deleted as it has program rules data associated with it.",gradProgramOptional.get().getProgramCode()));
			return 0;
		}else {
			gradProgramRepository.deleteById(programCode);
			return 1;
		}		
	}

	public GradProgramRule createGradProgramRules(@Valid GradProgramRule gradProgramRule,String accessToken) {
		GradProgramRulesEntity toBeSavedObject = gradProgramRulesTransformer.transformToEntity(gradProgramRule);
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		UUID existingObjectCheck = gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode());
		if(existingObjectCheck != null) {
			validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Program Code [%s]",gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode()));
			return gradProgramRule;			
		}else {	
			try {
				restTemplate.exchange(String.format(getRequirementTypeByCodeURL,gradProgramRule.getRequirementType()), HttpMethod.GET,
	    				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
				return gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.save(toBeSavedObject));
				
			}catch(Exception e) {
				 logger.debug("Exception:" + e);
		         validation.addErrorAndStop(String.format("Requirement Type [%s] is not Valid",gradProgramRule.getRequirementType()));
		         throw new GradBusinessRuleException();
			}
			
		}
	}

	public GradProgramRule updateGradProgramRules(@Valid GradProgramRule gradProgramRule, String accessToken) {
		GradProgramRulesEntity sourceObject = gradProgramRulesTransformer.transformToEntity(gradProgramRule);
		HttpHeaders httpHeaders = EducGradProgramManagementApiUtils.getHeaders(accessToken);
		Optional<GradProgramRulesEntity> gradProgramRulesOptional = gradProgramRulesRepository.findById(gradProgramRule.getId());
		if(gradProgramRulesOptional.isPresent()) {
			GradProgramRulesEntity gradRuleEnity = gradProgramRulesOptional.get();	
			if(checkIfRuleCodeChanged(gradRuleEnity,sourceObject)) {
				BeanUtils.copyProperties(sourceObject,gradRuleEnity,"createdBy","createdTimestamp");
				UUID existingObjectCheck = gradProgramRulesRepository.findIdByRuleCode(gradRuleEnity.getRuleCode(), gradRuleEnity.getProgramCode());
				if(existingObjectCheck != null) {
					validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Program Code [%s]",gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode()));
					return gradProgramRule;			
				}else {	
					try {
						restTemplate.exchange(String.format(getRequirementTypeByCodeURL,gradProgramRule.getRequirementType()), HttpMethod.GET,
			    				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
						return gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.save(gradRuleEnity));
						
					}catch(Exception e) {
						 logger.debug("Exception:" + e);
				         validation.addErrorAndStop(String.format("Requirement Type [%s] is not Valid",gradProgramRule.getRequirementType()));
				         throw new GradBusinessRuleException();
					}
					
				}
			}else {
				try {
					BeanUtils.copyProperties(sourceObject,gradRuleEnity,"createdBy","createdTimestamp");
					restTemplate.exchange(String.format(getRequirementTypeByCodeURL,gradProgramRule.getRequirementType()), HttpMethod.GET,
		    				new HttpEntity<>(httpHeaders), GradRequirementTypes.class).getBody();
					return gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.save(gradRuleEnity));
					
				}catch(Exception e) {
					 logger.debug("Exception:" + e);
			         validation.addErrorAndStop(String.format("Requirement Type [%s] is not Valid",gradProgramRule.getRequirementType()));
			         throw new GradBusinessRuleException();
				}
			}
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradProgramRule;
		}
	}
	
	private boolean checkIfRuleCodeChanged(GradProgramRulesEntity gradRuleEnity, GradProgramRulesEntity sourceObject) {
		if(!sourceObject.getRuleCode().equals(gradRuleEnity.getRuleCode())) {
			return true;
		}
		return false;		
	}

	public int deleteGradProgramRules(UUID programRuleID) {
		Optional<GradProgramRulesEntity> gradProgramRuleOptional = gradProgramRulesRepository.findById(programRuleID);
		if(gradProgramRuleOptional.isPresent()) {
			gradProgramRulesRepository.deleteById(programRuleID);
			return 1;
		}else {
			validation.addErrorAndStop("This Program Rule does not exists.");
			return 0;			
		}
	}

	public Boolean getRequirementByRequirementType(String typeCode) {
		List<GradProgramRulesEntity> gradList = gradProgramRulesRepository.existsByRequirementTypeCode(typeCode);
		if(gradList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public GradProgram getSpecificProgram(String programCode) {
		return gradProgramTransformer.transformToDTO(gradProgramRepository.findById(programCode));
	}
}
