package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.service.DepartmentService;
import kg.iaau.edu.diploma.ist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("department/departmentIndex");
        modelAndView.addObject("items", departmentService.getAllActive());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute Department department) {
        this.departmentService.save(department);
        return "redirect:/department/index";
    }
}
