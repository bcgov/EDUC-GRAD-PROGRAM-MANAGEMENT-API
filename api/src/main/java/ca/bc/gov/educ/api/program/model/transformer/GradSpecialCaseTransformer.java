package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradSpecialCase;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialCaseEntity;


@Component
public class GradSpecialCaseTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradSpecialCase transformToDTO (GradSpecialCaseEntity gradProgramEntity) {
    	GradSpecialCase gradSpecialCase = modelMapper.map(gradProgramEntity, GradSpecialCase.class);
        return gradSpecialCase;
    }

    public GradSpecialCase transformToDTO ( Optional<GradSpecialCaseEntity> gradProgramEntity ) {
    	GradSpecialCaseEntity cae = new GradSpecialCaseEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradSpecialCase gradSpecialCase = modelMapper.map(cae, GradSpecialCase.class);
        return gradSpecialCase;
    }

	public List<GradSpecialCase> transformToDTO (List<GradSpecialCaseEntity> gradSpecialCaseEntities ) {
		List<GradSpecialCase> gradSpecialCaseList = new ArrayList<GradSpecialCase>();
        for (GradSpecialCaseEntity gradSpecialCaseEntity : gradSpecialCaseEntities) {
        	GradSpecialCase gradSpecialCase = new GradSpecialCase();
        	gradSpecialCase = modelMapper.map(gradSpecialCaseEntity, GradSpecialCase.class);            
        	gradSpecialCaseList.add(gradSpecialCase);
        }
        return gradSpecialCaseList;
    }

    public GradSpecialCaseEntity transformToEntity(GradSpecialCase gradSpecialCase) {
        GradSpecialCaseEntity gradSpecialCaseEntity = modelMapper.map(gradSpecialCase, GradSpecialCaseEntity.class);
        return gradSpecialCaseEntity;
    }
}
