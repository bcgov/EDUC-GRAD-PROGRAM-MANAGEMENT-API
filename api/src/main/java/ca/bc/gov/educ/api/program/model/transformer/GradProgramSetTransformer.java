package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.entity.GradProgramSetEntity;


@Component
public class GradProgramSetTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradProgramSet transformToDTO (GradProgramSetEntity gradProgramEntity) {
    	GradProgramSet gradProgramSet = modelMapper.map(gradProgramEntity, GradProgramSet.class);
        return gradProgramSet;
    }

    public GradProgramSet transformToDTO ( Optional<GradProgramSetEntity> gradProgramSetEntity ) {
    	GradProgramSetEntity cae = new GradProgramSetEntity();
        if (gradProgramSetEntity.isPresent())
            cae = gradProgramSetEntity.get();

        GradProgramSet GradProgramSet = modelMapper.map(cae, GradProgramSet.class);
        return GradProgramSet;
    }

	public List<GradProgramSet> transformToDTO (Iterable<GradProgramSetEntity> gradProgramSetEntities ) {
		List<GradProgramSet> programSetList = new ArrayList<GradProgramSet>();
        for (GradProgramSetEntity gradProgramSetEntity : gradProgramSetEntities) {
        	GradProgramSet programSet = new GradProgramSet();
        	programSet = modelMapper.map(gradProgramSetEntity, GradProgramSet.class);            
        	programSetList.add(programSet);
        }
        return programSetList;
    }

    public GradProgramSetEntity transformToEntity(GradProgramSet GradProgramSet) {
        GradProgramSetEntity gradProgramSetEntity = modelMapper.map(GradProgramSet, GradProgramSetEntity.class);
        return gradProgramSetEntity;
    }
}
