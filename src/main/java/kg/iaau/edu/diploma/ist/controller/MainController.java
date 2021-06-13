package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.service.EventsService;
import kg.iaau.edu.diploma.ist.service.NewsService;
import kg.iaau.edu.diploma.ist.service.TestExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/main")
public class MainController {

    private final NewsService newsService;
    private final EventsService eventsService;
    private final TestExamService testExamService;

    @Autowired
    public MainController(NewsService newsService, EventsService eventsService, TestExamService testExamService) {
        this.newsService = newsService;
        this.eventsService = eventsService;
        this.testExamService = testExamService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(){
        ModelAndView modelAndView = new ModelAndView("main/index");
        modelAndView.addObject("news", new ArrayList<>());
        modelAndView.addObject("events", new ArrayList<>());
        return modelAndView;
    }

    @RequestMapping(value = {"/exams"}, method = RequestMethod.GET)
    public ModelAndView getExams(){
        ModelAndView modelAndView = new ModelAndView("testExam/userExams");
        modelAndView.addObject("exams", this.testExamService.getAllActive());
        return modelAndView;
    }
}
