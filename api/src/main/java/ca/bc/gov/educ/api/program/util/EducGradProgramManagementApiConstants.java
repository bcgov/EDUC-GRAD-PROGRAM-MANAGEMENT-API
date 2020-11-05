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

    //Attribute Constants
    public static final String GET_ALL_PROGRAM_SETS_BY_PROGRAM_CODE = "/programsets/{programCode}";
    public static final String GET_ALL_PROGRAM_RULES = "/programrules";
    public static final String GET_ALL_SPECIAL_CASE__BY_SPECIAL_CODE = "/specialcase/{specialCode}";
    public static final String GET_ALL_LETTER_GRADE__BY_LETTER_GRADE = "/lettergrade/{letterGrade}";
    public static final String GET_ALL_SPECIFIC_PROGRAM_RULES_BY_RULE = "/programrules/{ruleCode}";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
}
