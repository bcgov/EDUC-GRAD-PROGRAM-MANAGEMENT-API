package ca.bc.gov.educ.api.program.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.program.model.dto.GradProgram;
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
}
