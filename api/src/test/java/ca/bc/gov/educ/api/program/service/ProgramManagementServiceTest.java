package ca.bc.gov.educ.api.program.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.dto.GradRuleDetails;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgram;
import ca.bc.gov.educ.api.program.model.entity.GradLetterGradeEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialCaseEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;
import ca.bc.gov.educ.api.program.repository.GradLetterGradeRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialCaseRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRulesRepository;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import ca.bc.gov.educ.api.program.util.GradValidation;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProgramManagementServiceTest {

	@Autowired
	private ProgramManagementService programManagementService;
	
	@MockBean
	private GradProgramRepository gradProgramRepository;
	
	@MockBean
	private GradLetterGradeRepository gradLetterGradeRepository;
	
	@MockBean
	private GradSpecialCaseRepository gradSpecialCaseRepository;
	
	@MockBean
	private GradProgramRulesRepository gradProgramRulesRepository;
	
	@MockBean
	private GradSpecialProgramRulesRepository gradSpecialProgramRulesRepository;
	
	@MockBean
	private GradSpecialProgramRepository gradSpecialProgramRepository;
	
	@Autowired
	GradValidation validation;
	
	@Mock
	WebClient webClient;
	
	@Test
	public void testGetAllProgramList() {
		List<GradProgramEntity> gradProgramList = new ArrayList<>();
		GradProgramEntity obj = new GradProgramEntity();
		obj.setProgramCode("2018-EN");
		obj.setProgramName("2018 Graduation Program");
		gradProgramList.add(obj);
		obj = new GradProgramEntity();
		obj.setProgramCode("1950-EN");
		obj.setProgramName("1950 Graduation Program");
		gradProgramList.add(obj);
		Mockito.when(gradProgramRepository.findAll()).thenReturn(gradProgramList);
		programManagementService.getAllProgramList();
	}
	
	@Test
	public void testGetSpecificProgramCode() {
		String programCode = "2018-EN";
		GradProgram obj = new GradProgram();
		obj.setProgramCode("2018-EN");
		obj.setProgramName("2018 Graduation Program");
		GradProgramEntity objEntity = new GradProgramEntity();
		objEntity.setProgramCode("1950-EN");
		objEntity.setProgramName("1950 Graduation Program");
		Optional<GradProgramEntity> ent = Optional.of(objEntity);
		Mockito.when(gradProgramRepository.findById(programCode)).thenReturn(ent);
		programManagementService.getSpecificProgram(programCode);
		Mockito.verify(gradProgramRepository).findById(programCode);
	}
	
	@Test
	public void testGetSpecificProgramCodeReturnsNull() {
		String programCode = "2018-EN";
		Mockito.when(gradProgramRepository.findById(programCode)).thenReturn(Optional.empty());
		programManagementService.getSpecificProgram(programCode);
		Mockito.verify(gradProgramRepository).findById(programCode);
	}
	
	@Test
	public void testGetAllLetterGradeList() {
		List<GradLetterGradeEntity> gradLettergradeList = new ArrayList<>();
		GradLetterGradeEntity obj = new GradLetterGradeEntity();
		obj.setGpaMarkValue("2.5");
		obj.setLetterGrade("C");
		obj.setPassFlag("Y");
		gradLettergradeList.add(obj);
		obj.setGpaMarkValue("1.5");
		obj.setLetterGrade("D");
		obj.setPassFlag("N");
		gradLettergradeList.add(obj);
		Mockito.when(gradLetterGradeRepository.findAll()).thenReturn(gradLettergradeList);
		programManagementService.getAllLetterGradesList();
	}
	
	@Test
	public void testGetSpecificLetterGradeCode() {
		String letterGrade = "C";
		GradLetterGrade obj = new GradLetterGrade();
		obj.setGpaMarkValue("2.5");
		obj.setLetterGrade("C");
		obj.setPassFlag("Y");
		GradLetterGradeEntity objEntity = new GradLetterGradeEntity();
		objEntity.setGpaMarkValue("2.5");
		objEntity.setLetterGrade("C");
		objEntity.setPassFlag("Y");
		Optional<GradLetterGradeEntity> ent = Optional.of(objEntity);
		Mockito.when(gradLetterGradeRepository.findById(letterGrade)).thenReturn(ent);
		programManagementService.getSpecificLetterGrade(letterGrade);
		Mockito.verify(gradLetterGradeRepository).findById(letterGrade);
	}
	
	@Test
	public void testGetSpecificLetterGradeCodeReturnsNull() {
		String letterGrade = "C";
		Mockito.when(gradLetterGradeRepository.findById(letterGrade)).thenReturn(Optional.empty());
		programManagementService.getSpecificLetterGrade(letterGrade);
		Mockito.verify(gradLetterGradeRepository).findById(letterGrade);
	}
	
	@Test
	public void testGetAllSpecialCaseList() {
		List<GradSpecialCaseEntity> gradSpecialCaseList = new ArrayList<>();
		GradSpecialCaseEntity obj = new GradSpecialCaseEntity();
		obj.setDescription("D");
		obj.setSpecialCase("C");
		obj.setPassFlag("Y");
		gradSpecialCaseList.add(obj);
		obj = new GradSpecialCaseEntity();
		obj.setDescription("G");
		obj.setSpecialCase("T");
		obj.setPassFlag("N");
		gradSpecialCaseList.add(obj);
		Mockito.when(gradSpecialCaseRepository.findAll()).thenReturn(gradSpecialCaseList);
		programManagementService.getAllSpecialCaseList();
	}
	
	@Test
	public void testGetSpecificSpecialCaseCode() {
		String letterGrade = "D";
		GradSpecialCase obj = new GradSpecialCase();
		obj.setDescription("D");
		obj.setSpecialCase("C");
		obj.setPassFlag("Y");
		GradSpecialCaseEntity objEntity = new GradSpecialCaseEntity();
		objEntity.setDescription("D");
		objEntity.setSpecialCase("C");
		objEntity.setPassFlag("Y");
		Optional<GradSpecialCaseEntity> ent = Optional.of(objEntity);
		Mockito.when(gradSpecialCaseRepository.findById(letterGrade)).thenReturn(ent);
		programManagementService.getSpecificSpecialCase(letterGrade);
		Mockito.verify(gradSpecialCaseRepository).findById(letterGrade);
	}
	
	@Test
	public void testGetSpecificSpecialCaseCodeReturnsNull() {
		String letterGrade = "D";
		Mockito.when(gradSpecialCaseRepository.findById(letterGrade)).thenReturn(Optional.empty());
		programManagementService.getSpecificSpecialCase(letterGrade);
		Mockito.verify(gradSpecialCaseRepository).findById(letterGrade);
	}
	
	@Test
	public void testGetSpecificRuleDetails() {
		String ruleCode = "100";
		List<GradProgramRulesEntity> gradProgramRule = new ArrayList<GradProgramRulesEntity>();
		GradProgramRulesEntity ruleObj = new GradProgramRulesEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		gradProgramRule.add(ruleObj);
		ruleObj = new GradProgramRulesEntity();
		ruleObj.setProgramCode("2018-EN");
		ruleObj.setRuleCode("100");
		ruleObj.setRequirementName("ABC");
		gradProgramRule.add(ruleObj);
		
		List<GradSpecialProgramRulesEntity> gradSpecialProgramRule = new ArrayList<GradSpecialProgramRulesEntity>();
		GradSpecialProgramRulesEntity specialRuleObj = new GradSpecialProgramRulesEntity();
		specialRuleObj.setSpecialProgramID(new UUID(1, 1));
		specialRuleObj.setRuleCode("100");
		specialRuleObj.setRequirementName("ABC");
		gradSpecialProgramRule.add(specialRuleObj);
		specialRuleObj = new GradSpecialProgramRulesEntity();
		specialRuleObj.setSpecialProgramID(new UUID(1, 1));
		specialRuleObj.setRuleCode("100");
		specialRuleObj.setRequirementName("ABC");
		gradSpecialProgramRule.add(specialRuleObj);
		
		GradSpecialProgramEntity specialProgramObj = new GradSpecialProgramEntity();
		specialProgramObj.setProgramCode("2018-EN");
		specialProgramObj.setSpecialProgramCode("FI");
		specialProgramObj.setSpecialProgramName("French Immersion");
		specialProgramObj.setId(new UUID(1, 1));
		
		UUID specialProgramID = new UUID(1, 1);
		
		Mockito.when(gradProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(gradProgramRule);
		Mockito.when(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(gradSpecialProgramRule);
		Mockito.when(gradSpecialProgramRepository.findById(specialProgramID)).thenReturn(Optional.of(specialProgramObj));
		List<GradRuleDetails> result = programManagementService.getSpecificRuleDetails(ruleCode);
		assertEquals(4,result.size());
	}
	
	@Test
	public void testGetSpecificRuleDetails_noAssociatedRuleDetails() {
		String ruleCode = "100";		
		Mockito.when(gradProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<GradProgramRulesEntity>());
		Mockito.when(gradSpecialProgramRulesRepository.findByRuleCode(ruleCode)).thenReturn(new ArrayList<GradSpecialProgramRulesEntity>());
		List<GradRuleDetails> result = programManagementService.getSpecificRuleDetails(ruleCode);
		assertEquals(0,result.size());
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradProgram_exception() {
		GradProgram gradProgram = new GradProgram();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGH");
		
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Optional<GradProgramEntity> ent = Optional.of(gradProgramEntity);
		Mockito.when(gradProgramRepository.findById(gradProgram.getProgramCode())).thenReturn(ent);
		programManagementService.createGradProgram(gradProgram);
		
	}
	
	@Test
	public void testCreateGradProgram() {
		GradProgram gradProgram = new GradProgram();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGH");	
		
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Mockito.when(gradProgramRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		Mockito.when(gradProgramRepository.save(gradProgramEntity)).thenReturn(gradProgramEntity);
		programManagementService.createGradProgram(gradProgram);
		
	}
	
	@Test
	public void testUpdateGradProgram() {
		GradProgram gradProgram = new GradProgram();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");	
		GradProgramEntity toBeSaved = new GradProgramEntity();
		toBeSaved.setProgramCode("ABCD");
		toBeSaved.setProgramName("EFGHF");	
		
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Optional<GradProgramEntity> ent = Optional.of(gradProgramEntity);
		Mockito.when(gradProgramRepository.findById(gradProgram.getProgramCode())).thenReturn(ent);
		Mockito.when(gradProgramRepository.save(gradProgramEntity)).thenReturn(toBeSaved);
		programManagementService.updateGradProgram(gradProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgram_excpetion() {
		GradProgram gradProgram = new GradProgram();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");
		Mockito.when(gradProgramRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		programManagementService.updateGradProgram(gradProgram);			
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgram_exception() {
		GradProgram gradProgram = new GradProgram();
		gradProgram.setProgramCode("ABCD");
		gradProgram.setProgramName("EFGHF");
		Mockito.when(gradProgramRepository.findById(gradProgram.getProgramCode())).thenReturn(Optional.empty());
		programManagementService.updateGradProgram(gradProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradProgram_exception_childrecords() {
		String programCode="2018-EN";
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Optional<GradProgramEntity> ent = Optional.of(gradProgramEntity);
		Mockito.when(gradProgramRepository.findIfChildRecordsExists(programCode)).thenReturn(ent);
		programManagementService.deleteGradPrograms(programCode);
	}
	
	@Test
	public void testDeleteGradProgram() {
		String programCode="2018-EN";
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Mockito.when(gradProgramRepository.findIfChildRecordsExists(programCode)).thenReturn(Optional.empty());
		Mockito.when(gradProgramRepository.findIfSpecialProgramsExists(programCode)).thenReturn(Optional.empty());
		programManagementService.deleteGradPrograms(programCode);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testDeleteGradProgram_exception_specialprogramcheck() {
		String programCode="2018-EN";
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Optional<GradProgramEntity> ent = Optional.of(gradProgramEntity);
		Mockito.when(gradProgramRepository.findIfChildRecordsExists(programCode)).thenReturn(Optional.empty());
		Mockito.when(gradProgramRepository.findIfSpecialProgramsExists(programCode)).thenReturn(ent);
		programManagementService.deleteGradPrograms(programCode);
	}
	
	@Test
	public void testDeleteGradProgramRules() {
		UUID ruleID=new UUID(1, 1);
		GradProgramRulesEntity gradProgramRulesEntity = new GradProgramRulesEntity();
		gradProgramRulesEntity.setRuleCode("100");
		gradProgramRulesEntity.setProgramCode("2018-EN");
		Mockito.when(gradProgramRulesRepository.findById(ruleID)).thenReturn(Optional.of(gradProgramRulesEntity));
		programManagementService.deleteGradProgramRules(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void deleteGradProgram_exception_exception() {
		UUID ruleID=new UUID(1, 1);
		GradProgramEntity gradProgramEntity = new GradProgramEntity();
		gradProgramEntity.setProgramCode("ABCD");
		gradProgramEntity.setProgramName("EFGH");
		Mockito.when(gradProgramRulesRepository.findById(ruleID)).thenReturn(Optional.empty());
		programManagementService.deleteGradProgramRules(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgram_exception() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Optional<GradSpecialProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(ent);
		programManagementService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testCreateGradSpecialProgram() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		Mockito.when(gradSpecialProgramRepository.save(gradSpecialProgramEntity)).thenReturn(gradSpecialProgramEntity);
		programManagementService.createGradSpecialProgram(gradSpecialProgram);
		
	}
	
	@Test
	public void testUpdateGradSpecialProgram() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setId(new UUID(1, 1));
		gradSpecialProgram.setSpecialProgramCode("FI");
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGH");	
		GradSpecialProgramEntity toBeSaved = new GradSpecialProgramEntity();
		toBeSaved.setProgramCode("ABCD");
		toBeSaved.setId(new UUID(1, 1));
		toBeSaved.setSpecialProgramCode("FI");
		toBeSaved.setSpecialProgramName("EFGH");	
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Optional<GradSpecialProgramEntity> ent = Optional.of(gradSpecialProgramEntity);
		Mockito.when(gradSpecialProgramRepository.findById(gradSpecialProgram.getId())).thenReturn(ent);
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		Mockito.when(gradSpecialProgramRepository.save(gradSpecialProgramEntity)).thenReturn(toBeSaved);
		programManagementService.updateGradSpecialPrograms(gradSpecialProgram);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgram_excpetion() {
		GradSpecialProgram gradSpecialProgram = new GradSpecialProgram();
		gradSpecialProgram.setProgramCode("ABCD");
		gradSpecialProgram.setSpecialProgramName("EFGHF");
		Mockito.when(gradSpecialProgramRepository.findByProgramCodeAndSpecialProgramCode(gradSpecialProgram.getProgramCode(),gradSpecialProgram.getSpecialProgramCode())).thenReturn(Optional.empty());
		programManagementService.updateGradSpecialPrograms(gradSpecialProgram);			
	}
	
	@Test
	public void testDeleteGradSpecialProgram() {
		UUID ruleID=new UUID(1, 1);
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findById(ruleID)).thenReturn(Optional.of(gradSpecialProgramEntity));
		programManagementService.deleteGradSpecialPrograms(ruleID);
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testDeleteGradSpecialProgram_exception() {
		UUID ruleID=new UUID(1, 1);
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.findById(ruleID)).thenReturn(Optional.empty());
		programManagementService.deleteGradSpecialPrograms(ruleID);
	}
	
}
