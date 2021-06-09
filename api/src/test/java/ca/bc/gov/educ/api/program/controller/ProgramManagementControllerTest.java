package ca.bc.gov.educ.api.program.controller;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.service.ProgramManagementService;
import ca.bc.gov.educ.api.program.util.GradValidation;
import ca.bc.gov.educ.api.program.util.MessageHelper;
import ca.bc.gov.educ.api.program.util.ResponseHelper;


@ExtendWith(MockitoExtension.class)
public class ProgramManagementControllerTest {

	@Mock
	private ProgramManagementService programManagementService;
	
	@Mock
	ResponseHelper response;
	
	@InjectMocks
	private ProgramManagementController programManagementController;
	
	@Mock
	GradValidation validation;
	
	@Mock
	MessageHelper messagesHelper;
	
	@Mock
	OAuth2AuthenticationDetails oAuth2AuthenticationDetails;
	
	@Mock
	SecurityContextHolder securityContextHolder;
	
	@Mock
	ResponseEntity<List<GradProgram>> ent;
	
	@Test
	public void testGetAllProgramList() {
		List<GradProgram> gradProgramList = new ArrayList<>();
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		gradProgramList.add(obj);
		obj = new GradProgram();
		obj.setProgramCode("AC");
		obj.setProgramName("Autobody");
		gradProgramList.add(obj);
		
		Mockito.when(programManagementService.getAllProgramList()).thenReturn(gradProgramList);
		programManagementController.getAllPrograms();
		Mockito.verify(programManagementService).getAllProgramList();
	}
	
	@Test
	public void testGetAllProgramList_emptyList() {		
		Mockito.when(programManagementService.getAllProgramList()).thenReturn(new ArrayList<>());
		ResponseEntity<List<GradProgram>> programList = programManagementController.getAllPrograms();
		Mockito.verify(programManagementService).getAllProgramList();
		assertNull(programList);
	}
	
	@Test
	public void testGetSpecificProgram() {
		String programCode="AB";
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programManagementService.getSpecificProgram(programCode)).thenReturn(obj);
		programManagementController.getSpecificProgram(programCode);
		Mockito.verify(programManagementService).getSpecificProgram(programCode);
	}
	
	@Test
	public void testGetSpecificProgram_noObject() {
		String programCode="AB";
		Mockito.when(programManagementService.getSpecificProgram(programCode)).thenReturn(null);
		ResponseEntity<GradProgram> obj = programManagementController.getSpecificProgram(programCode);
		Mockito.verify(programManagementService).getSpecificProgram(programCode);
		assertNull(obj);
	}
	
	@Test
	public void testdeleteGradPrograms() {
		String programCode = "DC";
		Mockito.when(programManagementService.deleteGradPrograms(programCode)).thenReturn(1);
		programManagementController.deleteGradPrograms(programCode);
		Mockito.verify(programManagementService).deleteGradPrograms(programCode);
	}
	
	@Test
	public void testCreateGradPrograms() {
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programManagementService.createGradProgram(obj)).thenReturn(obj);
		programManagementController.createGradPrograms(obj);
	}
	
	@Test
	public void testCreateGradPrograms_error() {
		GradProgram obj = new GradProgram();
		obj.setProgramName("Autobody");
		Mockito.when(programManagementService.createGradProgram(obj)).thenReturn(obj);
		programManagementController.createGradPrograms(obj);
	}
	
	@Test
	public void testUpdateGradPrograms() {
		GradProgram obj = new GradProgram();
		obj.setProgramCode("AB");
		obj.setProgramName("Autobody");
		Mockito.when(programManagementService.updateGradProgram(obj)).thenReturn(obj);
		programManagementController.updateGradPrograms(obj);
	}
	
	@Test
	public void testUpdateGradPrograms_error() {
		GradProgram obj = new GradProgram();
		obj.setProgramName("Autobody");
		Mockito.when(programManagementService.updateGradProgram(obj)).thenReturn(obj);
		programManagementController.updateGradPrograms(obj);
	}
	
	@Test
	public void testGetAllProgramRules() {
		String programCode="2018-EN";
		String requirementType="M";
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradProgramRule> gradProgramRuleList = new ArrayList<>();
		GradProgramRule ruleEntity = new GradProgramRule();
        ruleEntity.setId(new UUID(1, 1));
        ruleEntity.setIsActive("Y");
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity);
        GradProgramRule ruleEntity2 = new GradProgramRule();
        ruleEntity2.setId(new UUID(1, 1));
        ruleEntity2.setIsActive("Y");
        ruleEntity2.setProgramCode("2018-EN");
        ruleEntity2.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity2);
		
		Mockito.when(programManagementService.getAllProgramRuleList(programCode,requirementType,null)).thenReturn(gradProgramRuleList);
		programManagementController.getAllProgramsRules(programCode,requirementType);
	}
	
	@Test
	public void testCreateGradProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradProgramRule obj = new GradProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programManagementService.createGradProgramRules(obj,null)).thenReturn(obj);
		programManagementController.createGradProgramRules(obj);
	}
	
	@Test
	public void testCreateGradProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradProgramRule obj = new GradProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programManagementService.createGradProgramRules(obj,null)).thenReturn(obj);
		programManagementController.createGradProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradProgramRule obj = new GradProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programManagementService.updateGradProgramRules(obj, null)).thenReturn(obj);
		programManagementController.updateGradProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradProgramRule obj = new GradProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programManagementService.updateGradProgramRules(obj, null)).thenReturn(obj);
		programManagementController.updateGradProgramRules(obj);
	}
	
	@Test
	public void testDeleteGradProgramRules() {
		UUID ruleID = new UUID(1, 1);
		Mockito.when(programManagementService.deleteGradProgramRules(ruleID)).thenReturn(1);
		programManagementController.deleteGradProgramRules(ruleID.toString());		
		
	}
	
	
	@Test
	public void testGetAllLetterGrade() {
		List<GradLetterGrade> gradList = new ArrayList<>();
		GradLetterGrade obj = new GradLetterGrade();
		obj.setLetterGrade("A");
		obj.setPassFlag("Y");
		gradList.add(obj);
		obj = new GradLetterGrade();
		obj.setLetterGrade("B");
		obj.setPassFlag("Y");
		gradList.add(obj);
		
		Mockito.when(programManagementService.getAllLetterGradesList()).thenReturn(gradList);
		programManagementController.getAllLetterGrades();
		Mockito.verify(programManagementService).getAllLetterGradesList();
	}
	
	@Test
	public void testGetAllSpecialCase() {
		List<GradSpecialCase> gradList = new ArrayList<>();
		GradSpecialCase obj = new GradSpecialCase();
		obj.setSpecialCase("A");
		obj.setPassFlag("Y");
		gradList.add(obj);
		obj = new GradSpecialCase();
		obj.setSpecialCase("B");
		obj.setPassFlag("Y");
		gradList.add(obj);
		
		Mockito.when(programManagementService.getAllSpecialCaseList()).thenReturn(gradList);
		programManagementController.getAllSpecialCases();
		Mockito.verify(programManagementService).getAllSpecialCaseList();
	}
	
	@Test
	public void testGetSpecificSpecialCase() {
		String specialCase="A";
		GradSpecialCase obj = new GradSpecialCase();
		obj.setSpecialCase("A");
		obj.setPassFlag("Y");
		Mockito.when(programManagementService.getSpecificSpecialCase(specialCase)).thenReturn(obj);
		programManagementController.getSpecificSpecialCases(specialCase);
		Mockito.verify(programManagementService).getSpecificSpecialCase(specialCase);
	}
	
	@Test
	public void testGetSpecificSpecialCase_noObject() {
		String specialCase="AB";
		Mockito.when(programManagementService.getSpecificSpecialCase(specialCase)).thenReturn(null);
		ResponseEntity<GradSpecialCase> obj = programManagementController.getSpecificSpecialCases(specialCase);
		Mockito.verify(programManagementService).getSpecificSpecialCase(specialCase);
		assertNull(obj);
	}
	
	@Test
	public void testGetSpecificRuleDetails() {
		String ruleCode="200";
		List<GradRuleDetails> gradRuleDetails = new ArrayList<>();
		GradRuleDetails ruleEntity = new GradRuleDetails();
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementName("Match");
        ruleEntity.setRuleCode("200");
        ruleEntity.setSpecialProgramCode("FI");
        gradRuleDetails.add(ruleEntity);
        GradRuleDetails ruleEntity2 = new GradRuleDetails();
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementName("Match");
        ruleEntity.setRuleCode("200");
        ruleEntity.setSpecialProgramCode("FI");
        gradRuleDetails.add(ruleEntity2);
        
        Mockito.when(programManagementService.getSpecificRuleDetails(ruleCode)).thenReturn(gradRuleDetails);
        programManagementController.getSpecificRuleDetails(ruleCode);
	}
	
	@Test
	public void testGetSpecificLetterGrade() {
		String letterGrade="AB";
		GradLetterGrade obj = new GradLetterGrade();
		obj.setLetterGrade("AB");
		obj.setPassFlag("Y");
		Mockito.when(programManagementService.getSpecificLetterGrade(letterGrade)).thenReturn(obj);
		programManagementController.getSpecificLetterGrade(letterGrade);
		Mockito.verify(programManagementService).getSpecificLetterGrade(letterGrade);
	}
}
