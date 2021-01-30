package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.service.ExamQuestionService;
import kg.iaau.edu.diploma.ist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/examQuestion")
public class ExamQuestionController {

    private final ExamQuestionService examQuestionService;

    @Autowired
    public ExamQuestionController(ExamQuestionService examQuestionService) {
        this.examQuestionService = examQuestionService;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("examQuestion/examQuestionIndex");
        modelAndView.addObject("items", examQuestionService.getAllActive());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute ExamQuestion examQuestion) {
        this.examQuestionService.save(examQuestion);
        return "redirect:/user/index";
    }
}
