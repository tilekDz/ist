package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.service.FacultyService;
import kg.iaau.edu.diploma.ist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "templates/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("faculty/facultyIndex");
        modelAndView.addObject("items", facultyService.getAllActive());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute Faculty faculty) {
        this.facultyService.save(faculty);
        return "redirect:/faculty/index";
    }
}
