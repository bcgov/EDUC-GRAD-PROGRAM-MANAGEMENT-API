package ca.bc.gov.educ.api.program.util;

import java.util.Date;

public class EducGradProgramManagementApiConstants {

    //API end-point Mapping constants
    public static final String API_ROOT_MAPPING = "";
    public static final String API_VERSION = "v1";
    public static final String GRAD_PROGRAM_MANAGEMENT_API_ROOT_MAPPING = "/api/" + API_VERSION + "/programmanagement";
    public static final String GET_ALL_PROGRAM_MAPPING = "/programs";

    //Attribute Constants
    public static final String GET_ALL_PROGRAM_SETS_BY_PROGRAM_CODE = "/programsets/{programCode}";
    public static final String GET_ALL_PROGRAM_RULES = "/programrules";

    //Default Attribute value constants
    public static final String DEFAULT_CREATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_CREATED_TIMESTAMP = new Date();
    public static final String DEFAULT_UPDATED_BY = "ProgramManagementAPI";
    public static final Date DEFAULT_UPDATED_TIMESTAMP = new Date();

    //Default Date format constants
    public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";
    
    public static final String TRAX_DATE_FORMAT = "yyyyMM";
}
