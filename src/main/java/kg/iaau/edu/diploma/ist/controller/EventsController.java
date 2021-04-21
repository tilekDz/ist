package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Events;
import kg.iaau.edu.diploma.ist.entity.News;
import kg.iaau.edu.diploma.ist.model.EventsPattern;
import kg.iaau.edu.diploma.ist.model.NewsPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.EventsService;
import kg.iaau.edu.diploma.ist.service.NewsService;
import kg.iaau.edu.diploma.ist.specification.EventsSpecification;
import kg.iaau.edu.diploma.ist.specification.NewsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kg.iaau.edu.diploma.ist.pagination.PaginationConstant.BUTTONS_TO_SHOW;

@Controller
@RequestMapping(value = "/events")
public class EventsController {
    private final EventsService eventsService;

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("events/index", new EventsPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        Events news = eventsService.getById(id);
        model.addAttribute("event", news);
        return new ModelAndView("events/detail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("events/edit");
        modelAndView.addObject("item", new Events());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Events news, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] image = file.getBytes();
        news.setActive(true);
        news.setImage(image);
        this.eventsService.save(news);
        return "redirect:/events/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchFaculty(@ModelAttribute EventsPattern pattern, BindingResult br,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size){
        return getModelAndView("events/index", pattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listFaculty(
            @ModelAttribute EventsPattern pattern, BindingResult br,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        pattern.setTitle(title);
        pattern.setDescription(description);
        return getModelAndView("events/index", pattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteFaculty(@PathVariable("id") Long id) {
        Events news = eventsService.getById(id);
        if (news != null) {
            eventsService.delete(news);
        }
        return new ModelAndView("redirect:/events/index");
    }

    private ModelAndView getModelAndView(String view, EventsPattern pattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<Events> specification = new EventsSpecification(pattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<Events> events = eventsService.getAllEvents(specification, pageable);

        int totalPages = events.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(events.getTotalPages(), events.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("events", events);
        modelAndView.addObject("pattern", pattern);
        return modelAndView;
    }
}
