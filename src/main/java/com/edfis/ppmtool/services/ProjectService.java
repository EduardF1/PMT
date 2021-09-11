package com.edfis.ppmtool.services;

import com.edfis.ppmtool.domain.Backlog;
import com.edfis.ppmtool.domain.Project;
import com.edfis.ppmtool.exceptions.ProjectIdException;
import com.edfis.ppmtool.repositories.BacklogRepository;
import com.edfis.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        String projectIdentifier = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(projectIdentifier);
            handleSaveOrUpdate(project, project.getId(), projectIdentifier);

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

    private void handleSaveOrUpdate(Project project, Long id, String projectIdentifier) {
        // Branching based on the project id, if an id exists for the given project, then
        // the backlog projectIdentifier is set to that id.
        if (id == null) {
            Backlog backlog = new Backlog();
            project.setBacklog(backlog);
            backlog.setProject(project);
            backlog.setProjectIdentifier(projectIdentifier);
        }

        if (id != null) project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
    }
}
