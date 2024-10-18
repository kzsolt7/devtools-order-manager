package hu.devtools.devtoolsordermanager.controller;

import hu.devtools.devtoolsordermanager.entity.Customer;
import hu.devtools.devtoolsordermanager.entity.Note;
import hu.devtools.devtoolsordermanager.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String listCustomers(Model model) {
        model.addAttribute("pageContent", "pages/admin/customers/list");
        model.addAttribute("customers", customerService.getAllCustomers());
        return "layout/layout";
    }

    @GetMapping("/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("pageContent", "pages/admin/customers/create");
        model.addAttribute("customer", new Customer());
        return "layout/layout";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "layout/layout";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("pageContent", "pages/admin/customers/edit"); // ugyanaz a form
        model.addAttribute("customer", customer);
        return "layout/layout";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "layout/layout";
        }
        customerService.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    // Jegyzet hozzáadása
    @GetMapping("/notes/{id}")
    public String addNoteForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("pageContent", "pages/admin/customers/notes");
        model.addAttribute("customer", customer);
        model.addAttribute("note", new Note());
        return "layout/layout";
    }

    @PostMapping("/notes/{id}")
    public String saveNote(@PathVariable Long id, @Valid @ModelAttribute Note note, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "layout/layout";
        }
        customerService.addNoteToCustomer(id, note);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
