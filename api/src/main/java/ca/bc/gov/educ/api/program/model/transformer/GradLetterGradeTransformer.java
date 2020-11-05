package ca.bc.gov.educ.api.program.model.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.educ.api.program.model.dto.GradLetterGrade;
import ca.bc.gov.educ.api.program.model.entity.GradLetterGradeEntity;


@Component
public class GradLetterGradeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradLetterGrade transformToDTO (GradLetterGradeEntity gradProgramEntity) {
    	GradLetterGrade gradLetterGrade = modelMapper.map(gradProgramEntity, GradLetterGrade.class);
        return gradLetterGrade;
    }

    public GradLetterGrade transformToDTO ( Optional<GradLetterGradeEntity> gradProgramEntity ) {
    	GradLetterGradeEntity cae = new GradLetterGradeEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradLetterGrade gradLetterGrade = modelMapper.map(cae, GradLetterGrade.class);
        return gradLetterGrade;
    }

	public List<GradLetterGrade> transformToDTO (Iterable<GradLetterGradeEntity> gradLetterGradeEntities ) {
		List<GradLetterGrade> gradLetterGradeList = new ArrayList<GradLetterGrade>();
        for (GradLetterGradeEntity gradLetterGradeEntity : gradLetterGradeEntities) {
        	GradLetterGrade gradLetterGrade = new GradLetterGrade();
        	gradLetterGrade = modelMapper.map(gradLetterGradeEntity, GradLetterGrade.class);            
        	gradLetterGradeList.add(gradLetterGrade);
        }
        return gradLetterGradeList;
    }

    public GradLetterGradeEntity transformToEntity(GradLetterGrade gradLetterGrade) {
        GradLetterGradeEntity gradLetterGradeEntity = modelMapper.map(gradLetterGrade, GradLetterGradeEntity.class);
        return gradLetterGradeEntity;
    }
}
