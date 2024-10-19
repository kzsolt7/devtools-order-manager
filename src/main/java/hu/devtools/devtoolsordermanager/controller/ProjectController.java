package hu.devtools.devtoolsordermanager.controller;

import hu.devtools.devtoolsordermanager.entity.Project;
import hu.devtools.devtoolsordermanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers/{customerId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // List projects for a specific customer
    @GetMapping
    public String listProjects(@PathVariable Long customerId, Model model) {
        model.addAttribute("pageContent", "pages/admin/customers/projects/list");
        model.addAttribute("projects", projectService.getAllProjectsForCustomer(customerId));
        model.addAttribute("customerId", customerId);
        return "layout/layout"; // Reusing layout template
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
}
