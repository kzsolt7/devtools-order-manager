package hu.devtools.devtoolsordermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pageContent", "pages/admin/home");
        return "layout/layout";
    }
}
