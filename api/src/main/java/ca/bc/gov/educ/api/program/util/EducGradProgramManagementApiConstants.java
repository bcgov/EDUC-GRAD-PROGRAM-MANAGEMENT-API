package ca.bc.gov.educ.api.program.util;

import java.util.Date;

public class EducGradProgramManagementApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_PROGRAM_MANAGEMENT_API_ROOT_MAPPING = "/api/" + API_VERSION + "/programmanagement";
    public static final String GET_ALL_PROGRAM_MAPPING = "/programs";
    public static final String GET_ALL_SPECIAL_CASE_MAPPING = "/specialcase";
    public static final String GET_ALL_LETTER_GRADE_MAPPING = "/lettergrade";
    public static final String DELETE_PROGRAM_MAPPING = "/programs/{programCode}";
    public static final String DELETE_PROGRAM_RULES_MAPPING = "/programrules/{programRuleID}";

    //Attribute Constants
    public static final String GET_ALL_PROGRAM_SETS_BY_PROGRAM_CODE = "/programsets/{programCode}";
    public static final String GRAD_PROGRAM_SETS = "/programsets";
    public static final String GET_ALL_PROGRAM_RULES = "/programrules";
    public static final String GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE = "/specialcase/{specialCode}";
    public static final String GET_ALL_LETTER_GRADE__BY_LETTER_GRADE = "/lettergrade/{letterGrade}";
    public static final String GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE = "/programrules/{ruleCode}";
    
    public static final String ENDPOINT_PROGRAM_TYPE_BY_CODE_URL = "${endpoint.code-api.program-type_by_code.url}";
    public static final String ENDPOINT_REQUIREMENT_TYPE_BY_CODE_URL = "${endpoint.code-api.requirement-type_by_code.url}";
    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
}
