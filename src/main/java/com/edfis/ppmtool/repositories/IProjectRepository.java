package com.edfis.ppmtool.repositories;

import com.edfis.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository extends CrudRepository<Project, Long> {

    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);
}
