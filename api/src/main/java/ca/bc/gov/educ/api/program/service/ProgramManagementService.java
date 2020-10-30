package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRules;
import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramRulesTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramSetTransformer;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramTransformer;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramSetRepository;

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

	public List<GradProgramSet> getAllProgramSetList(String programCode) {
		List<GradProgramSet> programSetList  = new ArrayList<GradProgramSet>();
        try {
        	programSetList = gradProgramSetTransformer.transformToDTO(gradProgramSetRepository.findByGradProgramCode(programCode));            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }

        return programSetList;
	}

	public List<GradProgramRules> getAllProgramRuleList(String programCode, String programSet, String requirementType) {
		UUID programSetID = gradProgramSetRepository.findIdByGradProgramCodeAndProgramSet(programCode,programSet);
		List<GradProgramRules> programRuleList  = new ArrayList<GradProgramRules>();
        try {
        	programRuleList = gradProgramRulesTransformer.transformToDTO(gradProgramRulesRepository.findByProgramSetIDAndRequirementType(programSetID,requirementType));            
        } catch (Exception e) {
            logger.debug("Exception:" + e);
        }
        return programRuleList;
	}
}
