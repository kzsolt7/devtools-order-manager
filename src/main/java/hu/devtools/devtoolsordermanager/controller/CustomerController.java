package hu.devtools.devtoolsordermanager.controller;

import hu.devtools.devtoolsordermanager.entity.Customer;
import hu.devtools.devtoolsordermanager.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String listCustomers(Model model) {
        model.addAttribute("pageContent", "pages/customers/list");
        model.addAttribute("customers", customerService.getAllCustomers());
        return "layout/layout";
    }

    @GetMapping("/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("pageContent", "pages/customers/create");
        model.addAttribute("customer", new Customer());
        return "layout/layout";
    }


    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/create";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }
}

