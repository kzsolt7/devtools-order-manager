package hu.devtools.devtoolsordermanager.service;

import hu.devtools.devtoolsordermanager.entity.Customer;
import hu.devtools.devtoolsordermanager.entity.Project;
import hu.devtools.devtoolsordermanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomerService customerService;

    public List<Project> getAllProjectsForCustomer(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return customer.getProjects();
    }

    public Project createProjectForCustomer(Long customerId, Project project) {
        Customer customer = customerService.getCustomerById(customerId);
        project.setCustomer(customer);
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void updateProject(Long projectId, Project projectDetails) {
        Project existingProject = getProjectById(projectId);
        existingProject.setName(projectDetails.getName());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setPrice(projectDetails.getPrice());
        existingProject.setAmountPaid(projectDetails.getAmountPaid());
        projectRepository.save(existingProject);
    }

    public void updateProjectPayment(Long projectId, BigDecimal amountPaid) {
        Project project = getProjectById(projectId);
        project.setAmountPaid(amountPaid);
        projectRepository.save(project);
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
    }
}
