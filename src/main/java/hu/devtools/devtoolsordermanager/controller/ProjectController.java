package hu.devtools.devtoolsordermanager.controller;

import hu.devtools.devtoolsordermanager.entity.Customer;
import hu.devtools.devtoolsordermanager.entity.Payment;
import hu.devtools.devtoolsordermanager.entity.Project;
import hu.devtools.devtoolsordermanager.service.CustomerService;
import hu.devtools.devtoolsordermanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customers/{customerId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final CustomerService customerService;

    // List projects for a specific customer
    @GetMapping
    public String listProjects(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Project> projects = projectService.getAllProjectsForCustomer(customerId);
        projects.forEach(project -> {
            project.setRemainingPayment(project.getRemainingPayment());
            project.setTotalAmountPaid(project.getTotalAmountPaid());
        });
        model.addAttribute("pageContent", "pages/admin/customers/projects/list");
        model.addAttribute("projects", projects);
        model.addAttribute("customer", customer);
        return "layout/layout";
    }

    // Show the form to create a new project
    @GetMapping("/create")
    public String createProjectForm(@PathVariable Long customerId, Model model) {
        model.addAttribute("pageContent", "pages/admin/customers/projects/create"); // Form page for project creation
        model.addAttribute("project", new Project());
        model.addAttribute("customerId", customerId);
        return "layout/layout"; // Reusing layout template
    }

    // Handle the creation of a new project
    @PostMapping("/create")
    public String createProject(@PathVariable Long customerId, @ModelAttribute Project project) {
        projectService.createProjectForCustomer(customerId, project);
        return "redirect:/customers/" + customerId + "/projects";
    }

    // Show the form to edit an existing project
    @GetMapping("/edit/{projectId}")
    public String editProjectForm(@PathVariable Long customerId, @PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("pageContent", "pages/admin/customers/projects/edit"); // Form page for project editing
        model.addAttribute("project", project);
        model.addAttribute("customerId", customerId); // Pass customerId for form submission
        return "layout/layout"; // Reusing layout template
    }

    // Handle the update of an existing project
    @PostMapping("/edit/{projectId}")
    public String updateProject(@PathVariable Long customerId, @PathVariable Long projectId, @ModelAttribute Project projectDetails) {
        projectService.updateProject(projectId, projectDetails);
        return "redirect:/customers/" + customerId + "/projects";
    }

    // Show the form to add a payment to a project
    @GetMapping("/payment/{projectId}")
    public String addPaymentForm(@PathVariable Long customerId, @PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("pageContent", "pages/admin/customers/projects/payment");
        model.addAttribute("project", project);
        model.addAttribute("payments", project.getPayments());
        model.addAttribute("customerId", customerId);
        return "layout/layout";
    }

    @PostMapping("/payment/add/{projectId}")
    public String addPayment(@PathVariable Long customerId, @PathVariable Long projectId, @RequestParam BigDecimal amount, @RequestParam String description, @RequestParam String invoiceNumber) {
        Project project = projectService.getProjectById(projectId);
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setDescription(description);
        payment.setInvoiceNumber(invoiceNumber);
        payment.setProject(project);
        project.getPayments().add(payment);
        projectService.saveProject(project);
        return "redirect:/customers/" + customerId + "/projects";
    }

    @PostMapping("/payment/update/{projectId}")
    public String updatePayment(@PathVariable Long customerId, @PathVariable Long projectId, @RequestParam BigDecimal amountPaid) {
        projectService.updateProjectPayment(projectId, amountPaid);
        return "redirect:/customers/" + customerId + "/projects";
    }
}