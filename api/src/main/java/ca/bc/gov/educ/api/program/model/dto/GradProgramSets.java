package ca.bc.gov.educ.api.program.model.dto;

import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradProgramSets {
    List<GradProgramSet> gradProgramSetList;
}
