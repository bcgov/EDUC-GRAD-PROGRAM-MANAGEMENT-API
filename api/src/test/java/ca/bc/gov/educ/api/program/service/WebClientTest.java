package ca.bc.gov.educ.api.program.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.After;
import org.junit.Before;
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

import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgramRule;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramEntity;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;
import ca.bc.gov.educ.api.program.repository.GradProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradProgramRulesRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRepository;
import ca.bc.gov.educ.api.program.repository.GradSpecialProgramRulesRepository;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiConstants;
import ca.bc.gov.educ.api.program.util.GradBusinessRuleException;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes","unchecked"})
public class WebClientTest {

    @MockBean
    WebClient webClient;

    @Autowired
    private EducGradProgramManagementApiConstants constants;

    @Autowired
	private ProgramManagementService programManagementService;
    
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    @Mock
    private Mono<GradRequirementTypes> monoResponse;
	
    @MockBean
	private GradProgramRepository gradProgramRepository;
	
    @MockBean
	private GradProgramRulesRepository gradProgramRulesRepository;
    
	@MockBean
	private GradSpecialProgramRulesRepository gradSpecialProgramRulesRepository;
	
	@MockBean
	private GradSpecialProgramRepository gradSpecialProgramRepository;
	
	
	
	@Before
    public void setUp() {
        openMocks(this);
    }

    

	@After
    public void tearDown() {

    }
    
    

    @Test
    public void testGetAllProgramRuleList() {
    	String programCode = "2018-EN"; 
    	String requirementType = "M";
    	GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        final List<GradProgramRulesEntity> gradProgramRuleList = new ArrayList<>();
        final GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
        ruleEntity.setId(new UUID(1, 1));
        ruleEntity.setIsActive("Y");
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity);
        final GradProgramRulesEntity ruleEntity2 = new GradProgramRulesEntity();
        ruleEntity2.setId(new UUID(1, 1));
        ruleEntity2.setIsActive("Y");
        ruleEntity2.setProgramCode("2018-EN");
        ruleEntity2.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity2);

        when(gradProgramRulesRepository.findByProgramCodeAndRequirementType(programCode,requirementType)).thenReturn(gradProgramRuleList);
        List<GradProgramRule> result = programManagementService.getAllProgramRuleList(programCode,requirementType,"accessToken");
        assertEquals(2, result.size());
        
    }
    
    @Test
    public void testGetAllProgramRuleList_requirementTypeBlank() {
    	String programCode = "2018-EN"; 
    	String requirementType = null;
    	GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), "M"))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        final List<GradProgramRulesEntity> gradProgramRuleList = new ArrayList<>();
        final GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
        ruleEntity.setId(new UUID(1, 1));
        ruleEntity.setIsActive("Y");
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity);
        final GradProgramRulesEntity ruleEntity2 = new GradProgramRulesEntity();
        ruleEntity2.setId(new UUID(1, 1));
        ruleEntity2.setIsActive("Y");
        ruleEntity2.setProgramCode("2018-EN");
        ruleEntity2.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity2);

        when(gradProgramRulesRepository.findByProgramCodeAndRequirementType(programCode,requirementType)).thenReturn(gradProgramRuleList);
        List<GradProgramRule> result = programManagementService.getAllProgramRuleList(programCode,requirementType,"accessToken");
        assertEquals(2, result.size());
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testGetAllProgramRuleList_requirementTypeBlank_returnsError() {
    	String programCode = "2018-EN"; 
    	String requirementType = null;
    	GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        final List<GradProgramRulesEntity> gradProgramRuleList = new ArrayList<>();
        final GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
        ruleEntity.setId(new UUID(1, 1));
        ruleEntity.setIsActive("Y");
        ruleEntity.setProgramCode("2018-EN");
        ruleEntity.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity);
        final GradProgramRulesEntity ruleEntity2 = new GradProgramRulesEntity();
        ruleEntity2.setId(new UUID(1, 1));
        ruleEntity2.setIsActive("Y");
        ruleEntity2.setProgramCode("2018-EN");
        ruleEntity2.setRequirementType("M");
        gradProgramRuleList.add(ruleEntity2);

        when(gradProgramRulesRepository.findByProgramCodeAndRequirementType(programCode,requirementType)).thenReturn(gradProgramRuleList);
        List<GradProgramRule> result = programManagementService.getAllProgramRuleList(programCode,requirementType,"accessToken");
        assertEquals(2, result.size());
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradProgramRules_existingRecordCheck() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(new UUID(1, 1));
    	programManagementService.createGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test
	public void testCreateGradProgramRules() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setId(new UUID(1, 1));
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(null);
    	Mockito.when(gradProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.createGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradProgramRules_requirementTypeError() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(null);
    	programManagementService.createGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test
	public void testUpdateGradProgramRules() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setId(new UUID(1, 1));
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(gradProgramRulesRepository.findById(gradProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(null);
    	Mockito.when(gradProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_ruleCodeChanged() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setId(new UUID(1, 1));
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("800");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(gradProgramRulesRepository.findById(gradProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_ruleCodeUnChanged() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setId(new UUID(1, 1));
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
    	GradProgramRulesEntity ruleEntity = new GradProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setProgramCode("2018-EN");
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("F");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
        
        Mockito.when(gradProgramRulesRepository.findById(gradProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradProgramRulesRepository.findIdByRuleCode(gradProgramRule.getRuleCode(),gradProgramRule.getProgramCode())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradProgramRules(gradProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradProgramRules_wrongRecord() {
    	GradProgramRule gradProgramRule = new GradProgramRule();
    	gradProgramRule.setId(new UUID(1, 1));
    	gradProgramRule.setProgramCode("2018-EN");
    	gradProgramRule.setRuleCode("100");
    	gradProgramRule.setRequirementType("M");
        
        Mockito.when(gradProgramRulesRepository.findById(gradProgramRule.getId())).thenReturn(Optional.empty());    
    	programManagementService.updateGradProgramRules(gradProgramRule, "accessToken");
	}
    
    //
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgramRules_existingRecordCheck() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		
		GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	programManagementService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test
	public void testCreateGradSpecialProgramRules() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setSpecialProgramID(new UUID(2,2));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(2,2));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
    	
        GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
        
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testCreateGradSpecialProgramRules_requirementTypeError() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	programManagementService.createGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test
	public void testUpdateGradSpecialProgramRules() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(null);
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_ruleCodeChanged() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("800");
		ruleEntity.setRequirementType("M");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(reqType); 
        
    	GradSpecialProgramEntity gradSpecialProgramEntity = new GradSpecialProgramEntity();
		gradSpecialProgramEntity.setProgramCode("ABCD");
		gradSpecialProgramEntity.setId(new UUID(1, 1));
		gradSpecialProgramEntity.setSpecialProgramCode("FI");
		gradSpecialProgramEntity.setSpecialProgramName("EFGH");
		Mockito.when(gradSpecialProgramRepository.getOne(gradSpecialProgramRule.getSpecialProgramID())).thenReturn(gradSpecialProgramEntity);
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_ruleCodeUnChanged() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
    	GradSpecialProgramRulesEntity ruleEntity = new GradSpecialProgramRulesEntity();
    	ruleEntity.setId(new UUID(1,1));
		ruleEntity.setSpecialProgramID(new UUID(1,1));
		ruleEntity.setRuleCode("100");
		ruleEntity.setRequirementType("F");
		String requirementType = "M";
		GradRequirementTypes reqType = new GradRequirementTypes();
    	reqType.setCode("M");
    	reqType.setDescription("Match");
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(GradRequirementTypes.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(null); 
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.of(ruleEntity));
    	Mockito.when(gradSpecialProgramRulesRepository.findIdByRuleCode(gradSpecialProgramRule.getRuleCode(),gradSpecialProgramRule.getSpecialProgramID())).thenReturn(new UUID(1, 1));
    	Mockito.when(gradSpecialProgramRulesRepository.save(ruleEntity)).thenReturn(ruleEntity);
    	programManagementService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
    @Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradSpecialProgramRules_wrongRecord() {
    	GradSpecialProgramRule gradSpecialProgramRule = new GradSpecialProgramRule();
    	gradSpecialProgramRule.setId(new UUID(1, 1));
    	gradSpecialProgramRule.setProgramCode("2018-EN");
    	gradSpecialProgramRule.setRuleCode("100");
    	gradSpecialProgramRule.setRequirementType("M");
        
        Mockito.when(gradSpecialProgramRulesRepository.findById(gradSpecialProgramRule.getId())).thenReturn(Optional.empty());    
    	programManagementService.updateGradSpecialProgramRules(gradSpecialProgramRule, "accessToken");
	}
    
}

