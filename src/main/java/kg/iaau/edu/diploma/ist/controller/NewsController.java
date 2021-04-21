package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.News;
import kg.iaau.edu.diploma.ist.model.NewsPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.NewsService;
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
@RequestMapping(value = "/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("news/index", new NewsPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        News news = newsService.getById(id);
        model.addAttribute("news", news);
        return new ModelAndView("news/detail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("news/edit");
        modelAndView.addObject("item", new News());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute News news, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] image = file.getBytes();
        news.setActive(true);
        news.setImage(image);
        this.newsService.save(news);
        return "redirect:/news/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchFaculty(@ModelAttribute NewsPattern pattern, BindingResult br,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size){
        return getModelAndView("news/index", pattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listFaculty(
            @ModelAttribute NewsPattern pattern, BindingResult br,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        pattern.setTitle(title);
        pattern.setDescription(description);
        return getModelAndView("news/index", pattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteFaculty(@PathVariable("id") Long id) {
        News news = newsService.getById(id);
        if (news != null) {
            newsService.delete(news);
        }
        return new ModelAndView("redirect:/news/index");
    }

    private ModelAndView getModelAndView(String view, NewsPattern pattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<News> specification = new NewsSpecification(pattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<News> allNews = newsService.getAllNews(specification, pageable);

        int totalPages = allNews.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(allNews.getTotalPages(), allNews.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("allNews", allNews);
        modelAndView.addObject("pattern", pattern);
        return modelAndView;
    }
}
