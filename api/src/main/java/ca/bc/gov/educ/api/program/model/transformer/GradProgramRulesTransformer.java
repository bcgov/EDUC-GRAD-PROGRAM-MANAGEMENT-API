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
    	gradProgramRule.setActiveDate(EducGradProgramManagementApiUtils.parseTraxDate(gradProgramEntity.getActiveDate() != null ? gradProgramEntity.getActiveDate().toString():null));
        return gradProgramRule;
    }

    public GradProgramRule transformToDTO (Optional<GradProgramRulesEntity> gradProgramSetEntity ) {
    	GradProgramRulesEntity cae = new GradProgramRulesEntity();
        if (gradProgramSetEntity.isPresent())
            cae = gradProgramSetEntity.get();

        GradProgramRule gradProgramRule = modelMapper.map(cae, GradProgramRule.class);
        gradProgramRule.setActiveDate(EducGradProgramManagementApiUtils.parseTraxDate(cae.getActiveDate() != null ? cae.getActiveDate().toString():null));
        return gradProgramRule;
    }

	public List<GradProgramRule> transformToDTO (Iterable<GradProgramRulesEntity> gradProgramSetEntities ) {
		List<GradProgramRule> programSetList = new ArrayList<GradProgramRule>();
        for (GradProgramRulesEntity gradProgramSetEntity : gradProgramSetEntities) {
        	GradProgramRule programSet = new GradProgramRule();
        	programSet = modelMapper.map(gradProgramSetEntity, GradProgramRule.class);
        	programSet.setActiveDate(EducGradProgramManagementApiUtils.parseTraxDate(programSet.getActiveDate() != null ? programSet.getActiveDate().toString():null));
        	programSetList.add(programSet);
        }
        return programSetList;
    }

    public GradProgramRulesEntity transformToEntity(GradProgramRule gradProgramRule) {
        GradProgramRulesEntity gradProgramSetEntity = modelMapper.map(gradProgramRule, GradProgramRulesEntity.class);
        gradProgramSetEntity.setActiveDate(gradProgramRule.getActiveDate() != null ?Date.valueOf(gradProgramRule.getActiveDate()) : null);
        return gradProgramSetEntity;
    }
}
