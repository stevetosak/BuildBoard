package com.db.finki.www.build_board.converter;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements Converter<String, Project> {

    private final ProjectService projectService;
    private final String REGEX_TO_CHECK_IF_ITS_A_NUMBER="^[0-9]+$";

    public ProjectConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public Project convert(@NonNull String something) {
        if(something.matches(REGEX_TO_CHECK_IF_ITS_A_NUMBER)) {
            Long id = Long.parseLong(something);
            return projectService.getById(id);
        }else{
            String title = something;
            return projectService.getByTitle(title);
        }
    }

}
