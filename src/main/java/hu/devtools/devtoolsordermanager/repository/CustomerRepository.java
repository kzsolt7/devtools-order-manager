package hu.devtools.devtoolsordermanager.repository;

import hu.devtools.devtoolsordermanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
