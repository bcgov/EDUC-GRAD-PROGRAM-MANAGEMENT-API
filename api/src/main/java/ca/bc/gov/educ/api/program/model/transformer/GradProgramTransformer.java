package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradProgram;
import ca.bc.gov.educ.api.program.model.entity.GradProgramEntity;


@Component
public class GradProgramTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradProgram transformToDTO (GradProgramEntity gradProgramEntity) {
    	GradProgram gradProgram = modelMapper.map(gradProgramEntity, GradProgram.class);
        return gradProgram;
    }

    public GradProgram transformToDTO ( Optional<GradProgramEntity> gradProgramEntity ) {
    	GradProgramEntity cae = new GradProgramEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradProgram gradProgram = modelMapper.map(cae, GradProgram.class);
        return gradProgram;
    }

	public List<GradProgram> transformToDTO (Iterable<GradProgramEntity> courseEntities ) {
		List<GradProgram> programList = new ArrayList<GradProgram>();
        for (GradProgramEntity courseEntity : courseEntities) {
        	GradProgram program = new GradProgram();
        	program = modelMapper.map(courseEntity, GradProgram.class);            
            programList.add(program);
        }
        return programList;
    }

    public GradProgramEntity transformToEntity(GradProgram gradProgram) {
        GradProgramEntity gradProgramEntity = modelMapper.map(gradProgram, GradProgramEntity.class);
        return gradProgramEntity;
    }
}
