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
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgram;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgramRule;
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
	
	@Test
	public void testGetRequirementByRequirementType() {
		String typeCode="AB";
		Mockito.when(programManagementService.getRequirementByRequirementType(typeCode)).thenReturn(true);
		programManagementController.getRequirementByRequirementType(typeCode);
		Mockito.verify(programManagementService).getRequirementByRequirementType(typeCode);
	}
	
	@Test
	public void testGetAllSpecialProgramsByID() {
		String specialProgramID=new UUID(1, 1).toString();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		Mockito.when(programManagementService.getSpecialProgramByID(UUID.fromString(specialProgramID))).thenReturn(gradSpecialProgram);
		programManagementController.getAllSpecialProgramsByID(specialProgramID);
	}
	
	@Test
	public void testGetAllSpecialPrograms() {
		List<GradSpecialProgram> list = new ArrayList<GradSpecialProgram>();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		Mockito.when(programManagementService.getAllSpecialProgramList()).thenReturn(list);
		programManagementController.getAllSpecialPrograms();
	}
	
	@Test
	public void testGetAllSpecialPrograms_emptyList() {
		Mockito.when(programManagementService.getAllSpecialProgramList()).thenReturn(new ArrayList<>());
		programManagementController.getAllSpecialPrograms();
	}
	
	@Test
	public void testGetAllSpecialProgramsByProgram() {
		String programCode = "2018-EN";
		List<GradSpecialProgram> list = new ArrayList<GradSpecialProgram>();
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		list.add(gradSpecialProgram);
		Mockito.when(programManagementService.getAllSpecialProgramList(programCode)).thenReturn(list);
		programManagementController.getAllSpecialPrograms(programCode);
	}
	
	@Test
	public void testGetAllSpecialProgramsByProgram_emptyList() {
		String programCode = "2018-EN";
		Mockito.when(programManagementService.getAllSpecialProgramList(programCode)).thenReturn(new ArrayList<>());
		programManagementController.getAllSpecialPrograms(programCode);
	}
	
	@Test
	public void testGetSpecialProgramsByProgramCodeSpecialProgramCode() {
		String programCode = "2018-EN";
		String specialProgramCode="FI";
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		Mockito.when(programManagementService.getSpecialProgram(programCode,specialProgramCode)).thenReturn(gradSpecialProgram);
		programManagementController.getSpecialPrograms(programCode,specialProgramCode);
	}
	
	@Test
	public void testCreateGradSpecialPrograms() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramCode("FI");
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programManagementService.createGradSpecialProgram(obj)).thenReturn(obj);
		programManagementController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testCreateGradSpecialPrograms_error() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programManagementService.createGradSpecialProgram(obj)).thenReturn(obj);
		programManagementController.createGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramCode("FI");
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programManagementService.updateGradSpecialPrograms(obj)).thenReturn(obj);
		programManagementController.updateGradSpecialPrograms(obj);
	}
	
	@Test
	public void testUpdateGradSpecialPrograms_error() {
		GradSpecialProgram obj = new GradSpecialProgram();
		obj.setProgramCode("AB");
		obj.setId(new UUID(1, 1));
		obj.setSpecialProgramName("French Immersion");
		Mockito.when(programManagementService.updateGradSpecialPrograms(obj)).thenReturn(obj);
		programManagementController.updateGradSpecialPrograms(obj);
	}
	
	@Test
	public void testDeleteSpecialPrograms() {
		String specialProgramID = new UUID(1, 1).toString();
		Mockito.when(programManagementService.deleteGradSpecialPrograms(UUID.fromString(specialProgramID))).thenReturn(1);
		programManagementController.deleteGradSpecialPrograms(specialProgramID);
	}
	
	@Test
	public void testGetSpecialProgramRules() {
		String specialProgramID = new UUID(1, 1).toString();
		String requirementType = "M";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	Mockito.when(programManagementService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,null)).thenReturn(list);
		programManagementController.getAllSpecialProgramRules(specialProgramID,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRules_emptyList() {
		String specialProgramID = new UUID(1, 1).toString();
		String requirementType = "M";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
    	Mockito.when(programManagementService.getAllSpecialProgramRuleList(UUID.fromString(specialProgramID),requirementType,null)).thenReturn(new ArrayList<>());
		programManagementController.getAllSpecialProgramRules(specialProgramID,requirementType);
	}
	
	@Test
	public void testCreateGradSpecialProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programManagementService.createGradSpecialProgramRules(obj,null)).thenReturn(obj);
		programManagementController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testCreateGradSpecialProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programManagementService.createGradSpecialProgramRules(obj,null)).thenReturn(obj);
		programManagementController.createGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		obj.setRequirementName("Match");
		Mockito.when(programManagementService.updateGradSpecialProgramRules(obj, null)).thenReturn(obj);
		programManagementController.updateGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testUpdateGradSpecialProgramRules_error() {
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		GradSpecialProgramRule obj = new GradSpecialProgramRule();
		obj.setProgramCode("AB");
		obj.setRuleCode("100");
		obj.setRequirementType("M");
		Mockito.when(programManagementService.updateGradSpecialProgramRules(obj, null)).thenReturn(obj);
		programManagementController.updateGradSpecialProgramRules(obj);
	}
	
	@Test
	public void testDeleteGradSpecialProgramRules() {
		UUID ruleID = new UUID(1, 1);
		Mockito.when(programManagementService.deleteGradSpecialProgramRules(ruleID)).thenReturn(1);
		programManagementController.deleteGradSpecialProgramRules(ruleID.toString());		
		
	}
	
	@Test
	public void testGetAllSpecialProgramRules() {
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programManagementService.getAllSpecialProgramRulesList(null)).thenReturn(list);
		programManagementController.getAllSpecialProgramRules();
	}
	
	@Test
	public void testGetAllSpecialProgramRules_emptyList() {
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programManagementService.getAllSpecialProgramRulesList(null)).thenReturn(new ArrayList<>());
		programManagementController.getAllSpecialProgramRules();
	}
	
	@Test
	public void getAllProgramsRules() {
		List<GradProgramRule> list = new ArrayList<>();
		GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	list.add(gradProgramRule);
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programManagementService.getAllProgramRulesList(null)).thenReturn(list);
		programManagementController.getAllProgramsRules();
	}
	
	@Test
	public void testGetAllProgramsRules_emptyList() {
    	
    	Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when(programManagementService.getAllProgramRulesList(null)).thenReturn(new ArrayList<>());
		programManagementController.getAllProgramsRules();
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode() {
		String specialProgramCode= "FI";
		String requirementType = "M";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
		Mockito.when(programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,null)).thenReturn(list);
		programManagementController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_emptyList() {
		String specialProgramCode= "FI";
		String requirementType = "M";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		
		Mockito.when(programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,requirementType,null)).thenReturn(new ArrayList<>());
		programManagementController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode,requirementType);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_withoutRequirementType() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		List<GradSpecialProgramRule> list = new ArrayList<>();
		GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	list.add(gradSpecialProgramRule);
    	
		Mockito.when(programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,null)).thenReturn(list);
		programManagementController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
	
	@Test
	public void testGetSpecialProgramRulesByProgramCodeAndSpecialProgramCode_withoutRequirementType_emptyList() {
		String specialProgramCode= "FI";
		String programCode="2018-EN";
		
		Authentication authentication = Mockito.mock(Authentication.class);
		OAuth2AuthenticationDetails details = Mockito.mock(OAuth2AuthenticationDetails.class);
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getDetails()).thenReturn(details);
		SecurityContextHolder.setContext(securityContext);
		
		
		Mockito.when(programManagementService.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode,specialProgramCode,null,null)).thenReturn(new ArrayList<>());
		programManagementController.getSpecialProgramRulesByProgramCodeAndSpecialProgramCode(programCode, specialProgramCode);
	}
}
