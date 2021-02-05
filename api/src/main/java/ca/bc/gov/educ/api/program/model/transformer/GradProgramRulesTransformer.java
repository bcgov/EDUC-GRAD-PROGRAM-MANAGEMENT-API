package ca.bc.gov.educ.api.program.model.transformer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradProgramRule;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiUtils;


@Component
public class GradProgramRulesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradProgramRule transformToDTO (GradProgramRulesEntity gradProgramEntity) {
    	GradProgramRule gradProgramRule = modelMapper.map(gradProgramEntity, GradProgramRule.class);
        return gradProgramRule;
    }

    public GradProgramRule transformToDTO (Optional<GradProgramRulesEntity> gradProgramRuleEntity ) {
    	GradProgramRulesEntity cae = new GradProgramRulesEntity();
        if (gradProgramRuleEntity.isPresent())
            cae = gradProgramRuleEntity.get();

        GradProgramRule gradProgramRule = modelMapper.map(cae, GradProgramRule.class);
        return gradProgramRule;
    }

	public List<GradProgramRule> transformToDTO (Iterable<GradProgramRulesEntity> gradProgramRuleEntities ) {
		List<GradProgramRule> programRuleList = new ArrayList<GradProgramRule>();
        for (GradProgramRulesEntity gradProgramRuleEntity : gradProgramRuleEntities) {
        	GradProgramRule programRule = new GradProgramRule();
        	programRule = modelMapper.map(gradProgramRuleEntity, GradProgramRule.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public GradProgramRulesEntity transformToEntity(GradProgramRule gradProgramRule) {
        GradProgramRulesEntity gradProgramRuleEntity = modelMapper.map(gradProgramRule, GradProgramRulesEntity.class);
        return gradProgramRuleEntity;
    }
}
