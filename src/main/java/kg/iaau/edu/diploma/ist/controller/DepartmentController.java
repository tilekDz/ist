package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Department;
import kg.iaau.edu.diploma.ist.model.DepartmentPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.DepartmentService;
import kg.iaau.edu.diploma.ist.service.FacultyService;
import kg.iaau.edu.diploma.ist.specification.DepartmentSpecification;
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
@RequestMapping(value = "/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final FacultyService facultyService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, FacultyService facultyService) {
        this.departmentService = departmentService;
        this.facultyService = facultyService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("department/departmentIndex", new DepartmentPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        return new ModelAndView("department/departmentDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("department/addDepartment");
        modelAndView.addObject("faculty", facultyService.getAllActive());
        modelAndView.addObject("item", new Department());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Department department) {
        this.departmentService.save(department);
        return "redirect:/department/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchDepartment(@ModelAttribute DepartmentPattern departmentPattern, BindingResult br,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("department/departmentIndex", departmentPattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listDepartment(
            @ModelAttribute DepartmentPattern departmentPattern, BindingResult br,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        departmentPattern.setName(name);
        departmentPattern.setDescription(description);
        return getModelAndView("department/departmentIndex", departmentPattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteDepartment(@PathVariable("id") Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            departmentService.delete(department);
        }
        return new ModelAndView("redirect:/department/index");
    }


    private ModelAndView getModelAndView(String view, DepartmentPattern departmentPattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<Department> specification = new DepartmentSpecification(departmentPattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "createdDate");

        Page<Department> departments = departmentService.getAllDepartments(specification, pageable);

        int totalPages = departments.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(departments.getTotalPages(), departments.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("departments", departments);
        modelAndView.addObject("pattern", departmentPattern);
        return modelAndView;
    }
}
