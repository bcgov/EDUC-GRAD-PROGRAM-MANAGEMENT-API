package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradSpecialProgram;
import ca.bc.gov.educ.api.program.model.entity.GradSpecialProgramEntity;


@Component
public class GradSpecialProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradSpecialProgram transformToDTO (GradSpecialProgramEntity gradSpecialProgramEntity) {
    	GradSpecialProgram gradSpecialProgram = modelMapper.map(gradSpecialProgramEntity, GradSpecialProgram.class);
        return gradSpecialProgram;
    }

    public GradSpecialProgram transformToDTO ( Optional<GradSpecialProgramEntity> gradSpecialProgramEntity ) {
    	GradSpecialProgramEntity cae = new GradSpecialProgramEntity();
        if (gradSpecialProgramEntity.isPresent())
            cae = gradSpecialProgramEntity.get();

        GradSpecialProgram gradSpecialProgram = modelMapper.map(cae, GradSpecialProgram.class);
        return gradSpecialProgram;
    }

	public List<GradSpecialProgram> transformToDTO (Iterable<GradSpecialProgramEntity> courseEntities ) {
		List<GradSpecialProgram> programList = new ArrayList<GradSpecialProgram>();
        for (GradSpecialProgramEntity courseEntity : courseEntities) {
        	GradSpecialProgram program = new GradSpecialProgram();
        	program = modelMapper.map(courseEntity, GradSpecialProgram.class);            
            programList.add(program);
        }
        return programList;
    }

    public GradSpecialProgramEntity transformToEntity(GradSpecialProgram gradSpecialProgram) {
        GradSpecialProgramEntity gradSpecialProgramEntity = modelMapper.map(gradSpecialProgram, GradSpecialProgramEntity.class);
        return gradSpecialProgramEntity;
    }
}
