package com.edfis.ppmtool.services;

import com.edfis.ppmtool.domain.Project;
import com.edfis.ppmtool.exceptions.ProjectIdException;
import com.edfis.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Object findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        return project != null ? project : new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' already exists");
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }
}
