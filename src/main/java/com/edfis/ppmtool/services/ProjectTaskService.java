package com.edfis.ppmtool.services;

import com.edfis.ppmtool.domain.Backlog;
import com.edfis.ppmtool.domain.ProjectTask;
import com.edfis.ppmtool.exceptions.project.ProjectNotFoundException;
import com.edfis.ppmtool.repositories.BacklogRepository;
import com.edfis.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogPTSequence = backlog.getPTSequence();
            backlogPTSequence++;
            backlog.setPTSequence(backlogPTSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogPTSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);

        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String backlogId) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
    }
}
