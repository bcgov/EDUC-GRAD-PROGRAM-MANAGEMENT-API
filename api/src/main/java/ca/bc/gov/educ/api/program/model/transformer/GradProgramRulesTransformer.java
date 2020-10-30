package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradProgramRules;
import ca.bc.gov.educ.api.program.model.entity.GradProgramRulesEntity;


@Component
public class GradProgramRulesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradProgramRules transformToDTO (GradProgramRulesEntity gradProgramEntity) {
    	GradProgramRules gradProgramRules = modelMapper.map(gradProgramEntity, GradProgramRules.class);
        return gradProgramRules;
    }

    public GradProgramRules transformToDTO ( Optional<GradProgramRulesEntity> gradProgramSetEntity ) {
    	GradProgramRulesEntity cae = new GradProgramRulesEntity();
        if (gradProgramSetEntity.isPresent())
            cae = gradProgramSetEntity.get();

        GradProgramRules gradProgramRules = modelMapper.map(cae, GradProgramRules.class);
        return gradProgramRules;
    }

	public List<GradProgramRules> transformToDTO (Iterable<GradProgramRulesEntity> gradProgramSetEntities ) {
		List<GradProgramRules> programSetList = new ArrayList<GradProgramRules>();
        for (GradProgramRulesEntity gradProgramSetEntity : gradProgramSetEntities) {
        	GradProgramRules programSet = new GradProgramRules();
        	programSet = modelMapper.map(gradProgramSetEntity, GradProgramRules.class);            
        	programSetList.add(programSet);
        }
        return programSetList;
    }

    public GradProgramRulesEntity transformToEntity(GradProgramRules gradProgramRules) {
        GradProgramRulesEntity gradProgramSetEntity = modelMapper.map(gradProgramRules, GradProgramRulesEntity.class);
        return gradProgramSetEntity;
    }
}
