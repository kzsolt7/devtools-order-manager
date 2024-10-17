package hu.devtools.devtoolsordermanager.repository;

import hu.devtools.devtoolsordermanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

