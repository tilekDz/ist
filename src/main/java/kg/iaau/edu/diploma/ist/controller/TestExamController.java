package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.service.TestExamService;
import kg.iaau.edu.diploma.ist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/testExam")
public class TestExamController {

    private final TestExamService testExamService;

    @Autowired
    public TestExamController(TestExamService testExamService) {
        this.testExamService = testExamService;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("testExam/testExamIndex");
        modelAndView.addObject("items", testExamService.getAllActive());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute TestExam testExam) {
        this.testExamService.save(testExam);
        return "redirect:/testExam/index";
    }
}
