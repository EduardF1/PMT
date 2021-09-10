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

    public Object findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        return project != null ? project : new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' already exists");
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project with ID'" + projectId.toUpperCase() + "' does not exist.");
        }

        projectRepository.delete(project);
    }


    public Project updateProject(Project updatedProject) {

        updatedProject.setProjectIdentifier(updatedProject.getProjectIdentifier().toUpperCase());

        Project oldProject = projectRepository.findByProjectIdentifier(updatedProject.getProjectIdentifier());
        if (oldProject == null) {
            throw new ProjectIdException(String.format("Cannot update project as Project ID: %s does not exist", updatedProject.getProjectIdentifier()));
        }

        updatedProject.setId(oldProject.getId());
        return projectRepository.save(updatedProject);
    }
}
