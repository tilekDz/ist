package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.model.TestExamPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.*;
import kg.iaau.edu.diploma.ist.specification.TestExamSpecification;
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
@RequestMapping(value = "/testExam")
public class TestExamController {

    private final TestExamService testExamService;
    private final ExamQuestionService examQuestionService;

    @Autowired
    public TestExamController(TestExamService testExamService, ExamQuestionService examQuestionService) {
        this.testExamService = testExamService;
        this.examQuestionService = examQuestionService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("testExam/testExamIndex", new TestExamPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        TestExam testExam = testExamService.getTestExamById(id);
        model.addAttribute("testExam", testExam);
        return new ModelAndView("testExam/testExamDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("testExam/addTestExam");
        modelAndView.addObject("examQuestion", examQuestionService.getAllActive());
        modelAndView.addObject("item", new TestExam());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute TestExam testExam) {
        testExam.setActive(true);
        this.testExamService.save(testExam);
        return "redirect:/testExam/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchTestExam(@ModelAttribute TestExamPattern testExamPattern, BindingResult br,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size){
        return getModelAndView("testExam/testExamIndex", testExamPattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listTestExam(
            @ModelAttribute TestExamPattern testExamPattern, BindingResult br,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        testExamPattern.setName(name);
        testExamPattern.setDescription(description);
        return getModelAndView("testExam/testExamIndex", testExamPattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteTestExam(@PathVariable("id") Long id) {
        TestExam testExam = testExamService.getTestExamById(id);
        if (testExam != null) {
            testExamService.delete(testExam);
        }
        return new ModelAndView("redirect:/testExam/index");
    }


    private ModelAndView getModelAndView(String view, TestExamPattern testExamPattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<TestExam> specification = new TestExamSpecification(testExamPattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<TestExam> testExams = testExamService.getAllTestExams(specification, pageable);

        int totalPages = testExams.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(testExams.getTotalPages(), testExams.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("testExams", testExams);
        modelAndView.addObject("pattern", testExamPattern);
        return modelAndView;
    }
}
