package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Faculty;
import kg.iaau.edu.diploma.ist.model.FacultyPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.FacultyService;
import kg.iaau.edu.diploma.ist.specification.FacultySpecification;
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
@RequestMapping(value = "/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("faculty/facultyIndex", new FacultyPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        model.addAttribute("faculty", faculty);
        return new ModelAndView("faculty/facultyDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("faculty/addFaculty");
        modelAndView.addObject("item", new Faculty());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Faculty faculty) {
        faculty.setActive(true);
        this.facultyService.save(faculty);
        return "redirect:/faculty/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchFaculty(@ModelAttribute FacultyPattern facultyPattern, BindingResult br,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size){
        return getModelAndView("faculty/facultyIndex", facultyPattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listFaculty(
            @ModelAttribute FacultyPattern facultyPattern, BindingResult br,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        facultyPattern.setName(name);
        facultyPattern.setDescription(description);
        return getModelAndView("faculty/facultyIndex", facultyPattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteFaculty(@PathVariable("id") Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty != null) {
            facultyService.delete(faculty);
        }
        return new ModelAndView("redirect:/faculty/index");
    }


    private ModelAndView getModelAndView(String view, FacultyPattern facultyPattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<Faculty> specification = new FacultySpecification(facultyPattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<Faculty> faculties = facultyService.getAllFaculties(specification, pageable);

        int totalPages = faculties.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(faculties.getTotalPages(), faculties.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("faculties", faculties);
        modelAndView.addObject("pattern", facultyPattern);
        return modelAndView;
    }
}
