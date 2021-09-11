package com.edfis.ppmtool.controllers;

import com.edfis.ppmtool.domain.Project;
import com.edfis.ppmtool.services.ProjectService;
import com.edfis.ppmtool.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = validationErrorService.validate(result);
        if (errorMap != null) return errorMap;

        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = (Project) projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<>("Project with ID: '" + projectId + "' was deleted.", HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = validationErrorService.validate((result));
        if(errorMap != null)
            return errorMap;

        Project updatedProject = projectService.updateProject(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }
}
