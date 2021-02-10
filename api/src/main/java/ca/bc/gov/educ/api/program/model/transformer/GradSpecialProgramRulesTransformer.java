package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgramRule;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramRulesEntity;


@Component
public class GradSpecialProgramRulesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradSpecialProgramRule transformToDTO (GradSpecialProgramRulesEntity gradSpecialProgramEntity) {
    	GradSpecialProgramRule gradSpecialProgramRule = modelMapper.map(gradSpecialProgramEntity, GradSpecialProgramRule.class);
        return gradSpecialProgramRule;
    }

    public GradSpecialProgramRule transformToDTO (Optional<GradSpecialProgramRulesEntity> gradSpecialProgramRuleEntity ) {
    	GradSpecialProgramRulesEntity cae = new GradSpecialProgramRulesEntity();
        if (gradSpecialProgramRuleEntity.isPresent())
            cae = gradSpecialProgramRuleEntity.get();

        GradSpecialProgramRule gradSpecialProgramRule = modelMapper.map(cae, GradSpecialProgramRule.class);
        return gradSpecialProgramRule;
    }

	public List<GradSpecialProgramRule> transformToDTO (Iterable<GradSpecialProgramRulesEntity> gradSpecialProgramRuleEntities ) {
		List<GradSpecialProgramRule> programRuleList = new ArrayList<GradSpecialProgramRule>();
        for (GradSpecialProgramRulesEntity gradSpecialProgramRuleEntity : gradSpecialProgramRuleEntities) {
        	GradSpecialProgramRule programRule = new GradSpecialProgramRule();
        	programRule = modelMapper.map(gradSpecialProgramRuleEntity, GradSpecialProgramRule.class);
        	programRuleList.add(programRule);
        }
        return programRuleList;
    }

    public GradSpecialProgramRulesEntity transformToEntity(GradSpecialProgramRule gradSpecialProgramRule) {
        GradSpecialProgramRulesEntity gradSpecialProgramRuleEntity = modelMapper.map(gradSpecialProgramRule, GradSpecialProgramRulesEntity.class);
        return gradSpecialProgramRuleEntity;
    }
}
