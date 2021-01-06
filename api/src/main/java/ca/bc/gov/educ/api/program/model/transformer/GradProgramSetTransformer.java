package ca.bc.gov.educ.api.program.model.transformer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradProgramSet;
import ca.bc.gov.educ.api.program.model.entity.GradProgramSetEntity;
import ca.bc.gov.educ.api.program.util.EducGradProgramManagementApiUtils;


@Component
public class GradProgramSetTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradProgramSet transformToDTO (GradProgramSetEntity gradProgramEntity) {
    	GradProgramSet gradProgramSet = modelMapper.map(gradProgramEntity, GradProgramSet.class);
    	gradProgramSet.setProgramSetStartDate(EducGradProgramManagementApiUtils.parseTraxDate(gradProgramEntity.getProgramSetStartDate() != null ? gradProgramEntity.getProgramSetStartDate().toString():null));
    	gradProgramSet.setProgramSetEndDate(EducGradProgramManagementApiUtils.parseTraxDate(gradProgramEntity.getProgramSetEndDate() != null ? gradProgramEntity.getProgramSetEndDate().toString():null));
    	return gradProgramSet;
    }

    public GradProgramSet transformToDTO ( Optional<GradProgramSetEntity> gradProgramSetEntity ) {
    	GradProgramSetEntity cae = new GradProgramSetEntity();
        if (gradProgramSetEntity.isPresent())
            cae = gradProgramSetEntity.get();

        GradProgramSet gradProgramSet = modelMapper.map(cae, GradProgramSet.class);
        gradProgramSet.setProgramSetStartDate(EducGradProgramManagementApiUtils.parseTraxDate(cae.getProgramSetStartDate() != null ? cae.getProgramSetStartDate().toString():null));
        gradProgramSet.setProgramSetEndDate(EducGradProgramManagementApiUtils.parseTraxDate(cae.getProgramSetEndDate() != null ? cae.getProgramSetEndDate().toString():null));
        return gradProgramSet;
    }

	public List<GradProgramSet> transformToDTO (Iterable<GradProgramSetEntity> gradProgramSetEntities ) {
		List<GradProgramSet> programSetList = new ArrayList<GradProgramSet>();
        for (GradProgramSetEntity gradProgramSetEntity : gradProgramSetEntities) {
        	GradProgramSet programSet = new GradProgramSet();
        	programSet = modelMapper.map(gradProgramSetEntity, GradProgramSet.class); 
        	programSet.setProgramSetStartDate(EducGradProgramManagementApiUtils.parseTraxDate(programSet.getProgramSetStartDate() != null ? programSet.getProgramSetStartDate().toString():null));
        	programSet.setProgramSetEndDate(EducGradProgramManagementApiUtils.parseTraxDate(programSet.getProgramSetEndDate() != null ? programSet.getProgramSetEndDate().toString():null));
        	programSetList.add(programSet);
        }
        return programSetList;
    }

    public GradProgramSetEntity transformToEntity(GradProgramSet gradProgramSet) {
        GradProgramSetEntity gradProgramSetEntity = modelMapper.map(gradProgramSet, GradProgramSetEntity.class);
        gradProgramSetEntity.setProgramSetStartDate(gradProgramSet.getProgramSetStartDate() != null ?Date.valueOf(gradProgramSet.getProgramSetStartDate()) : null);
        gradProgramSetEntity.setProgramSetEndDate(gradProgramSet.getProgramSetEndDate() != null ?Date.valueOf(gradProgramSet.getProgramSetEndDate()) : null);
        return gradProgramSetEntity;
    }
}
