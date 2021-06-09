package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgram;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgramRule;
import ca.bc.gov.educ.api.program.model.entity.GradLetterGradeEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialCaseEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.transformer.GradLetterGradeTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramRulesTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradSpecialCaseTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradSpecialProgramRulesTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradSpecialProgramTransformer;
import ca.bc.gov.educ.api.program.repository.GradLetterGradeRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialCaseRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRulesRepository;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiConstants;
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
    private GradSpecialProgramRepository gradSpecialProgramRepository;  

    @Autowired
    private GradSpecialProgramTransformer gradSpecialProgramTransformer; 
    
    @Autowired
    private GradSpecialProgramRulesRepository gradSpecialProgramRulesRepository;     
    
    @Autowired
    private GradSpecialProgramRulesTransformer gradSpecialProgramRulesTransformer;
    
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
    
    @Autowired
	EducGradProgramManagementApiConstants educGradProgramManagementApiConstants;
    
    @Value("${validation.value.requirementType}")
	String errorStringRequirementTypeInvalid;
    
    @Value("${validation.value.ruleCode_programCode}")
	String errorStringRuleCodeProgramCodeAssociated;
    
    @Value("${validation.value.programcode}")
	String errorStringProgramCodeExists;
    
    @Value("${validation.value.programcode_rule_check}")
   	String errorStringProgramCodeRuleCheck;
    
    @Value("${validation.value.programcode_specialprogram_check}")
   	String errorStringProgramCodeSpecialProgramCheck;
    
    
    
    
    
	private static final String CREATED_BY="createdBy";
	private static final String CREATED_TIMESTAMP="createdTimestamp";
	private static final String CREATE="create";
	private static final String UPDATE="update";
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    WebClient webClient;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ProgramManagementService.class);

     /**
     * Get all Programs in Grad Program DTO
     *
     * @return GradProgram 
     * @throws java.lang.Exception
     */
    public List<GradProgram> getAllProgramList() {
        return gradProgramTransformer.transformToDTO(gradProgramRepository.findAll()); 
    }
    
	public List<GradProgramRule> getAllProgramRuleList(String programCode, String requirementType,String accessToken) {
		if(StringUtils.isNotBlank(requirementType)) {
			GradRequirementTypes gradReqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),requirementType)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
			if(gradReqType == null) {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,requirementType));
	    	}
	    }
      	List<GradProgramRule> programRuleList  = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByProgramCodeAndRequirementType(programCode,requirementType));   
    	programRuleList.forEach(pR-> {
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    	});       
        return programRuleList;
	}
	
	 public List<GradSpecialCase> getAllSpecialCaseList() {
	        return specialCaseTransformer.transformToDTO(gradSpecialCaseRepository.findAll());
	    }

	public GradSpecialCase getSpecificSpecialCase(String specialCode) {
		Optional<GradSpecialCaseEntity> gradResponse =gradSpecialCaseRepository.findById(specialCode); 
		if(gradResponse.isPresent()) {
			return specialCaseTransformer.transformToDTO(gradResponse.get());
		}
		return null;
	}

	public List<GradRuleDetails> getSpecificRuleDetails(String ruleCode) {
		List<GradRuleDetails> detailList = new ArrayList<>();
		List<GradProgramRule> gradProgramRule = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByRuleCode(ruleCode));
		if(!gradProgramRule.isEmpty()) {
			gradProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getRuleCode());
				details.setRequirementName(gpR.getRequirementName());			
				details.setProgramCode(gpR.getProgramCode());
				detailList.add(details);
			});			
		}
		List<GradSpecialProgramRule> gradSpecialProgramRule = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode));
		if(!gradSpecialProgramRule.isEmpty()) {
			gradSpecialProgramRule.forEach(gpR -> {
				GradRuleDetails details = new GradRuleDetails();
				details.setRuleCode(gpR.getRuleCode());
				details.setRequirementName(gpR.getRequirementName());	
				GradSpecialProgram gradSpecialProgram = gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(gpR.getSpecialProgramID()));
				details.setProgramCode(gradSpecialProgram.getProgramCode());
				details.setSpecialProgramCode(gradSpecialProgram.getSpecialProgramCode());
				detailList.add(details);
			});			
		}
		return detailList;
	}

	public List<GradLetterGrade> getAllLetterGradesList() {
		return gradLetterGradeTransformer.transformToDTO(gradLetterGradeRepository.findAll());
	}

	public GradLetterGrade getSpecificLetterGrade(String letterGrade) {
		Optional<GradLetterGradeEntity> gradResponse =gradLetterGradeRepository.findById(letterGrade); 
		if(gradResponse.isPresent()) {
			return gradLetterGradeTransformer.transformToDTO(gradResponse.get());
		}
		return null;
	}

	public GradProgram createGradProgram(GradProgram gradProgram) {
		GradProgramEntity toBeSavedObject = gradProgramTransformer.transformToEntity(gradProgram);
		Optional<GradProgramEntity> existingObjectCheck = gradProgramRepository.findById(gradProgram.getProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format(errorStringProgramCodeExists,gradProgram.getProgramCode()));
			return null;			
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
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATED_BY,CREATED_TIMESTAMP);
			return gradProgramTransformer.transformToDTO(gradProgramRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format(errorStringProgramCodeExists,gradProgram.getProgramCode()));
			return gradProgram;
		}
	}

	public int deleteGradPrograms(@Valid String programCode) {
		Optional<GradProgramEntity> gradProgramOptional = gradProgramRepository.findIfChildRecordsExists(programCode);
		if(gradProgramOptional.isPresent()) {
			validation.addErrorAndStop(String.format(errorStringProgramCodeRuleCheck,gradProgramOptional.get().getProgramCode()));
			return 0;
		}else {
			Optional<GradProgramEntity> gradSpecialProgramOptional = gradProgramRepository.findIfSpecialProgramsExists(programCode);
			if(gradSpecialProgramOptional.isPresent()) {
				validation.addErrorAndStop(String.format(errorStringProgramCodeSpecialProgramCheck,programCode));
				return 0;
			}else {
				gradProgramRepository.deleteById(programCode);
				return 1;
			}			
		}		
	}

	public GradProgramRule createGradProgramRules(@Valid GradProgramRule gradProgramRule,String accessToken) {
		GradProgramRulesEntity toBeSavedObject = gradProgramRulesTransformer.transformToEntity(gradProgramRule);
		UUID existingObjectCheck = gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode());
		if(existingObjectCheck != null) {
			validation.addErrorAndStop(String.format(errorStringRuleCodeProgramCodeAssociated,gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode()));
			return gradProgramRule;			
		}else {
			if(validateRequirementType(gradProgramRule.getRequirementType(),gradProgramRule.getRequirementType(),accessToken,CREATE)) {
				return gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.save(toBeSavedObject));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradProgramRule.getRequirementType()));
				return null;
			}			
		}
	}
	
	public boolean validateRequirementType(String toBeSavedRequirementType, String existingRequirementType,String accessToken,String task) {
		if(task.equalsIgnoreCase(CREATE) || !existingRequirementType.equalsIgnoreCase(toBeSavedRequirementType)) {
			GradRequirementTypes reqTypes = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),toBeSavedRequirementType)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
			return reqTypes != null;
		}
		return true;
	}

	public GradProgramRule updateGradProgramRules(@Valid GradProgramRule gradProgramRule, String accessToken) {
		GradProgramRulesEntity sourceObject = gradProgramRulesTransformer.transformToEntity(gradProgramRule);
		Optional<GradProgramRulesEntity> gradProgramRulesOptional = gradProgramRulesRepository.findById(gradProgramRule.getId());
		if(gradProgramRulesOptional.isPresent()) {
			GradProgramRulesEntity gradRuleEnity = gradProgramRulesOptional.get();
			
			if(checkIfRuleCodeChanged(gradRuleEnity,sourceObject)) {				
				UUID existingObjectCheck = gradProgramRulesRepository.findIdByRuleCode(sourceObject.getRuleCode(), sourceObject.getProgramCode());
				if(existingObjectCheck != null) {
					validation.addErrorAndStop(String.format(errorStringRuleCodeProgramCodeAssociated,gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode()));
					return gradProgramRule;			
				}
			}			
			if(validateRequirementType(gradProgramRule.getRequirementType(),gradRuleEnity.getRequirementType(),accessToken,UPDATE)) {
				BeanUtils.copyProperties(sourceObject,gradRuleEnity,CREATED_BY,CREATED_TIMESTAMP);
				return gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.save(gradRuleEnity));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradProgramRule.getRequirementType()));
				return null;
			}
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradProgramRule;
		}
	}
	
	private boolean checkIfRuleCodeChanged(GradProgramRulesEntity gradRuleEnity, GradProgramRulesEntity sourceObject) {
		return !sourceObject.getRuleCode().equals(gradRuleEnity.getRuleCode());		
	}
	
	private boolean checkIfSpecialRuleCodeChanged(GradSpecialProgramRulesEntity gradRuleEnity, GradSpecialProgramRulesEntity sourceObject) {
		return !sourceObject.getRuleCode().equals(gradRuleEnity.getRuleCode());
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
		return !gradList.isEmpty();
	}

	public GradProgram getSpecificProgram(String programCode) {
		Optional<GradProgramEntity> gradResponse = gradProgramRepository.findById(programCode); 
		if(gradResponse.isPresent()) {
			return gradProgramTransformer.transformToDTO(gradResponse.get());
		}
		return null;
	}

	public GradSpecialProgram createGradSpecialProgram(@Valid GradSpecialProgram gradSpecialProgram) {
		GradSpecialProgramEntity toBeSavedObject = gradSpecialProgramTransformer.transformToEntity(gradSpecialProgram);
		Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode());
		if(existingObjectCheck.isPresent()) {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] already exists",gradSpecialProgram.getSpecialProgramCode(),gradSpecialProgram.getProgramCode()));
			return gradSpecialProgram;			
		}else {
			return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.save(toBeSavedObject));
		}
	}
	
	@Transactional
	public GradSpecialProgram updateGradSpecialPrograms(GradSpecialProgram gradSpecialProgram) {
		Optional<GradSpecialProgramEntity> gradSpecialProgramOptional = gradSpecialProgramRepository.findById(gradSpecialProgram.getId());
		GradSpecialProgramEntity sourceObject = gradSpecialProgramTransformer.transformToEntity(gradSpecialProgram);
		if(gradSpecialProgramOptional.isPresent()) {			
			GradSpecialProgramEntity gradEnity = gradSpecialProgramOptional.get();
			if(checkIfProgramCodeandSpecialPogramCodeChanged(gradEnity,sourceObject)) {
				Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode());
				if(existingObjectCheck.isPresent()) {
					validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] already exists",gradSpecialProgram.getSpecialProgramCode(),gradSpecialProgram.getProgramCode()));
					return gradSpecialProgram;			
				}
			}
			BeanUtils.copyProperties(sourceObject,gradEnity,CREATED_BY,CREATED_TIMESTAMP);
			return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.save(gradEnity));
		}else {
			validation.addErrorAndStop(String.format("Special Program ID [%s] does not exists",gradSpecialProgram.getId()));
			return gradSpecialProgram;
		}
	}
	
	private boolean checkIfProgramCodeandSpecialPogramCodeChanged(GradSpecialProgramEntity gradSpecialProgramEntity, GradSpecialProgramEntity sourceObject) {
		return (!sourceObject.getProgramCode().equals(gradSpecialProgramEntity.getProgramCode()) || !sourceObject.getSpecialProgramCode().equals(gradSpecialProgramEntity.getSpecialProgramCode()));
				
	}

	public int deleteGradSpecialPrograms(UUID specialProgramID) {
		Optional<GradSpecialProgramEntity> gradSpecialProgramOptional = gradSpecialProgramRepository.findById(specialProgramID);
		if(gradSpecialProgramOptional.isPresent()) {
			gradSpecialProgramRepository.deleteById(specialProgramID);
			return 1;
		}else {
			validation.addErrorAndStop("This Special Program ID does not exists.");
			return 0;			
		}
	}

	public List<GradSpecialProgram> getAllSpecialProgramList(String programCode) {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findByProgramCode(programCode));
	}
	
	public List<GradSpecialProgramRule>  getAllSpecialProgramRuleList(UUID specialProgramID, String requirementType,String accessToken) {
		if(StringUtils.isNotBlank(requirementType)) {
    		GradRequirementTypes gradReqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),requirementType)).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
			if(gradReqType == null) {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,requirementType));
	    	}
        }
        List<GradSpecialProgramRule> programRuleList = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findBySpecialProgramIDAndRequirementType(specialProgramID,requirementType));   
    	programRuleList.forEach(pR-> {
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    	});
        return programRuleList;
	}

	public GradSpecialProgramRule createGradSpecialProgramRules(@Valid GradSpecialProgramRule gradSpecialProgramRule,
			String accessToken) {
		GradSpecialProgramRulesEntity toBeSavedObject = gradSpecialProgramRulesTransformer.transformToEntity(gradSpecialProgramRule);
		UUID existingObjectCheck = gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID());
		if(existingObjectCheck != null) {
			GradSpecialProgramEntity optional = gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID());
			validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Special Program Code [%s] and Program Code [%s] combination.",gradSpecialProgramRule.getRuleCode(),optional.getSpecialProgramCode(),optional.getProgramCode()));
			return gradSpecialProgramRule;			
		}else {	
			if(validateRequirementType(gradSpecialProgramRule.getRequirementType(),gradSpecialProgramRule.getRequirementType(),accessToken,CREATE)) {
					return gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.save(toBeSavedObject));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradSpecialProgramRule.getRequirementType()));
				return null;
			}	
		}
	}
	
	public GradSpecialProgramRule updateGradSpecialProgramRules(@Valid GradSpecialProgramRule gradSpecialProgramRule, String accessToken) {
		GradSpecialProgramRulesEntity sourceObject = gradSpecialProgramRulesTransformer.transformToEntity(gradSpecialProgramRule);
		Optional<GradSpecialProgramRulesEntity> gradSpecialProgramRulesOptional = gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId());
		if(gradSpecialProgramRulesOptional.isPresent()) {
			GradSpecialProgramRulesEntity gradRuleEnity = gradSpecialProgramRulesOptional.get();	
			if(checkIfSpecialRuleCodeChanged(gradRuleEnity,sourceObject)) {				
				UUID existingObjectCheck = gradSpecialProgramRulesRepository.findIdByRuleCode(sourceObject.getRuleCode(), sourceObject.getSpecialProgramID());
				if(existingObjectCheck != null) {
					GradSpecialProgramEntity optional = gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID());
					validation.addErrorAndStop(String.format("This Rule Code [%s] is already associated to a Special Program Code [%s] and Program Code [%s] combination.",gradSpecialProgramRule.getRuleCode(),optional.getSpecialProgramCode(),optional.getProgramCode()));
					return gradSpecialProgramRule;			
				}
			}
			if(validateRequirementType(gradSpecialProgramRule.getRequirementType(),gradRuleEnity.getRequirementType(),accessToken,UPDATE)) {
				BeanUtils.copyProperties(sourceObject,gradRuleEnity,CREATED_BY,CREATED_TIMESTAMP);
				return gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.save(gradRuleEnity));
			}else {
				validation.addErrorAndStop(String.format(errorStringRequirementTypeInvalid,gradSpecialProgramRule.getRequirementType()));
				return null;
			}			
		}else {
			validation.addErrorAndStop("Unique Identifier not found. Update Failed");
			return gradSpecialProgramRule;
		}
	}

	public int deleteGradSpecialProgramRules(UUID programRuleID) {
		Optional<GradSpecialProgramRulesEntity> gradSpecialProgramRuleOptional = gradSpecialProgramRulesRepository.findById(programRuleID);
		if(gradSpecialProgramRuleOptional.isPresent()) {
			gradProgramRulesRepository.deleteById(programRuleID);
			return 1;
		}else {
			validation.addErrorAndStop("This Program Rule does not exists.");
			return 0;			
		}
	}

	public List<GradSpecialProgram> getAllSpecialProgramList() {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findAll());      
	}

	public GradSpecialProgram getSpecialProgramByID(UUID specialProgramID) {
		return gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(specialProgramID));
	}

	public List<GradSpecialProgramRule> getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(String programCode,
			String specialProgramCode,String requirementType,String accessToken) {
		Optional<GradSpecialProgramEntity> existingObjectCheck = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
		if(existingObjectCheck.isPresent()) {
			return getAllSpecialProgramRuleList(existingObjectCheck.get().getId(),requirementType,accessToken);
		}else {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return new ArrayList<>();
		}
	}

	public GradSpecialProgram getSpecialProgram(String programCode, String specialProgramCode) {
		Optional<GradSpecialProgramEntity> optionalRec = gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
		if(optionalRec.isPresent()) {
			return gradSpecialProgramTransformer.transformToDTO(optionalRec.get());
		}else {
			validation.addErrorAndStop(String.format("Special Program Code [%s] and Program Code [%s] combination does not exist",specialProgramCode,programCode));
			return null;
		}
	}

	public List<GradProgramRule> getAllProgramRulesList(String accessToken) {
		List<GradProgramRule> programRuleList  = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findAll());   
    	programRuleList.forEach(pR-> {
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    	});
    	if(!programRuleList.isEmpty()) {
	    	Collections.sort(programRuleList, Comparator.comparing(GradProgramRule::getProgramCode)
	    			.thenComparing(GradProgramRule::getRuleCode));   
    	}
        return programRuleList;
	}
	
	public List<GradSpecialProgramRule>  getAllSpecialProgramRulesList(String accessToken) {
		List<GradSpecialProgramRule> programRuleList  = gradSpecialProgramRulesTransformer.transformToDTO(gradSpecialProgramRulesRepository.findAll());   
    	programRuleList.forEach(pR-> {
    		GradSpecialProgram gSp = gradSpecialProgramTransformer.transformToDTO(gradSpecialProgramRepository.findById(pR.getSpecialProgramID()));
    		GradRequirementTypes reqType = webClient.get().uri(String.format(educGradProgramManagementApiConstants.getGradRequirementTypeByCode(),pR.getRequirementType())).headers(h -> h.setBearerAuth(accessToken)).retrieve().bodyToMono(GradRequirementTypes.class).block();
    		pR.setRequirementTypeDesc(reqType.getDescription());
    		pR.setProgramCode(gSp.getProgramCode());
    		pR.setSpecialProgramCode(gSp.getSpecialProgramCode());
    	});
    	if(!programRuleList.isEmpty()) {
	    	Collections.sort(programRuleList, Comparator.comparing(GradSpecialProgramRule::getProgramCode)
	    			.thenComparing(GradSpecialProgramRule::getSpecialProgramCode)
	    			.thenComparing(GradSpecialProgramRule::getRuleCode));   
    	}
        return programRuleList;
	}
}
