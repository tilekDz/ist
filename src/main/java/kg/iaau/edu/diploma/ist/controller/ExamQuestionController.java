package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.model.ExamQuestionPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.service.*;
import kg.iaau.edu.diploma.ist.specification.ExamQuestionSpecification;
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
@RequestMapping(value = "/examQuestion")
public class ExamQuestionController {

    private final ExamQuestionService examQuestionService;
    private final SubjectService subjectService;

    @Autowired
    public ExamQuestionController(ExamQuestionService examQuestionService, SubjectService subjectService) {
        this.examQuestionService = examQuestionService;
        this.subjectService = subjectService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("examQuestion/examQuestionIndex", new ExamQuestionPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        ExamQuestion examQuestion = examQuestionService.getExamQuestionById(id);
        model.addAttribute("examQuestion", examQuestion);
        return new ModelAndView("examQuestion/examQuestionDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("examQuestion/addExamQuestion");
        modelAndView.addObject("subject", subjectService.getAllActive());
        modelAndView.addObject("item", new ExamQuestion());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute ExamQuestion examQuestion) {
        examQuestion.setActive(true);
        this.examQuestionService.save(examQuestion);
        return "redirect:/examQuestion/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchExamQuestion(@ModelAttribute ExamQuestionPattern examQuestionPattern, BindingResult br,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size){
        return getModelAndView("examQuestion/examQuestionIndex", examQuestionPattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listExamQuestion(
            @ModelAttribute ExamQuestionPattern examQuestionPattern, BindingResult br,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        examQuestionPattern.setName(name);
        examQuestionPattern.setDescription(description);
        return getModelAndView("examQuestion/examQuestionIndex", examQuestionPattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteExamQuestion(@PathVariable("id") Long id) {
        ExamQuestion examQuestion = examQuestionService.getExamQuestionById(id);
        if (examQuestion != null) {
            examQuestionService.delete(examQuestion);
        }
        return new ModelAndView("redirect:/examQuestion/index");
    }


    private ModelAndView getModelAndView(String view, ExamQuestionPattern examQuestionPattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<ExamQuestion> specification = new ExamQuestionSpecification(examQuestionPattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<ExamQuestion> examQuestions = examQuestionService.getAllExamQuestions(specification, pageable);

        int totalPages = examQuestions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(examQuestions.getTotalPages(), examQuestions.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("departments", examQuestions);
        modelAndView.addObject("pattern", examQuestionPattern);
        return modelAndView;
    }
}
