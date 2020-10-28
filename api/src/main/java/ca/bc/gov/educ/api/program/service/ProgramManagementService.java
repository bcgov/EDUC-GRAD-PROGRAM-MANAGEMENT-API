package ca.bc.gov.educ.api.program.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.transformer.GradProgramTransformer;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;

@Service
public class ProgramManagementService {

    @Autowired
    private GradProgramRepository gradProgramRepository;  

    @Autowired
    private GradProgramTransformer gradProgramTransformer;

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
}
