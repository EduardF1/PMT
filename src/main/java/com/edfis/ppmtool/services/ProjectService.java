package com.edfis.ppmtool.services;

import com.edfis.ppmtool.domain.Project;
import com.edfis.ppmtool.repositories.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private IProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        return projectRepository.save(project);
    }
}
