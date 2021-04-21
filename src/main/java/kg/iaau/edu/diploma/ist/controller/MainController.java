package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.service.EventsService;
import kg.iaau.edu.diploma.ist.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/main")
public class MainController {

    private final NewsService newsService;
    private final EventsService eventsService;

    @Autowired
    public MainController(NewsService newsService, EventsService eventsService) {
        this.newsService = newsService;
        this.eventsService = eventsService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(){
        ModelAndView modelAndView = new ModelAndView("main/index");
        modelAndView.addObject("news", newsService.findLastFourNews());
        modelAndView.addObject("events", eventsService.findLastThreeEvents());
        return modelAndView;
    }
}
