package hu.devtools.devtoolsordermanager.repository;

import hu.devtools.devtoolsordermanager.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}

