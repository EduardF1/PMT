package com.edfis.ppmtool.repositories;

import com.edfis.ppmtool.domain.Project;
import com.edfis.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectIdentifier(String projectId);
}
