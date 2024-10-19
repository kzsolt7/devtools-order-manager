package hu.devtools.devtoolsordermanager.repository;

import hu.devtools.devtoolsordermanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
