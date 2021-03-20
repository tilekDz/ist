package kg.iaau.edu.diploma.ist.controller;


import kg.iaau.edu.diploma.ist.entity.Subject;
import kg.iaau.edu.diploma.ist.model.SubjectPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.DepartmentService;
import kg.iaau.edu.diploma.ist.service.SubjectService;
import kg.iaau.edu.diploma.ist.specification.SubjectSpecification;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kg.iaau.edu.diploma.ist.pagination.PaginationConstant.BUTTONS_TO_SHOW;

@Controller
@RequestMapping(value = "/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final DepartmentService departmentService;

    @Autowired
    public SubjectController(SubjectService subjectService, DepartmentService departmentService) {
        this.subjectService = subjectService;
        this.departmentService = departmentService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("subject/subjectIndex", new SubjectPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);
        return new ModelAndView("subject/subjectDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("subject/addSubject");
        modelAndView.addObject("department", departmentService.getAllActive());
        modelAndView.addObject("item", new Subject());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Subject subject) {
        subject.setActive(true);
        this.subjectService.save(subject);
        return "redirect:/subject/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchSubject(@ModelAttribute SubjectPattern subjectPattern, BindingResult br,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size){
        return getModelAndView("subject/subjectIndex", subjectPattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listSubject(
            @ModelAttribute SubjectPattern subjectPattern, BindingResult br,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        subjectPattern.setName(name);
        subjectPattern.setDescription(description);
        return getModelAndView("subject/subjectIndex", subjectPattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteSubject(@PathVariable("id") Long id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            subjectService.delete(subject);
        }
        return new ModelAndView("redirect:/subject/index");
    }


    private ModelAndView getModelAndView(String view, SubjectPattern subjectPattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<Subject> specification = new SubjectSpecification(subjectPattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<Subject> subjects = subjectService.getAllSubject(specification, pageable);

        int totalPages = subjects.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(subjects.getTotalPages(), subjects.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("subjects", subjects);
        modelAndView.addObject("pattern", subjectPattern);
        return modelAndView;
    }
}
