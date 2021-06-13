package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.ExamHistory;
import kg.iaau.edu.diploma.ist.entity.ExamQuestion;
import kg.iaau.edu.diploma.ist.entity.TestExam;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.model.TestExamPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.repository.ExamHistoryRepository;
import kg.iaau.edu.diploma.ist.service.*;
import kg.iaau.edu.diploma.ist.specification.TestExamSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kg.iaau.edu.diploma.ist.pagination.PaginationConstant.BUTTONS_TO_SHOW;

@Controller
@RequestMapping(value = "/testExam")
public class TestExamController {

    private final TestExamService testExamService;
    private final SubjectService subjectService;
    private final ExamQuestionService examQuestionService;
    private final UserService userService;
    private final ExamHistoryRepository examHistoryRepository;

    @Autowired
    public TestExamController(TestExamService testExamService, SubjectService subjectService, ExamQuestionService examQuestionService, UserService userService, ExamHistoryRepository examHistoryRepository) {
        this.testExamService = testExamService;
        this.subjectService = subjectService;
        this.examQuestionService = examQuestionService;
        this.userService = userService;
        this.examHistoryRepository = examHistoryRepository;
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

    @RequestMapping("/getUserExam/{id}")
    public ModelAndView getUserExam(Model model, @PathVariable("id") Long id) {
        TestExam testExam = testExamService.getTestExamById(id);
        model.addAttribute("testExam", testExam);
        return new ModelAndView("testExam/testExamUserDetail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("testExam/addtestExam");
        modelAndView.addObject("subjects", subjectService.getAllActive());
        modelAndView.addObject("item", new TestExam());
        return modelAndView;
    }

    @RequestMapping("/getUserResult")
    public ModelAndView getUserResult(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof User) {
            username = ((User)principal).getEmail();
        } else {
            username = principal.toString();
        }
        User user = this.userService.getByEmail(username);
        ModelAndView modelAndView = new ModelAndView("testExam/testExamUserResultPage");
        modelAndView.addObject("exams", examHistoryRepository.findAllByUser(user));
        return modelAndView;
    }

    @RequestMapping("/getAllResult")
    public ModelAndView getAllResult(){
        ModelAndView modelAndView = new ModelAndView("testExam/testExamAllResultPage");
        modelAndView.addObject("exams", examHistoryRepository.findAllByDateIsNotNull());
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute TestExam testExam) {
        testExam.setActive(true);
        this.testExamService.save(testExam);
        return "redirect:/testExam/index";
    }

    @RequestMapping(value = "/takeExam", method = RequestMethod.POST)
    public ModelAndView takeExam(@ModelAttribute ExamHistory history, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        List<ExamQuestion> examQuestions = examQuestionService.findAllByTest(history.getTestExam());
        List<String> answers = new ArrayList<>();
        for (ExamQuestion eq : examQuestions) {
            answers.add(eq.getFirstAnswer());
            answers.add(eq.getSecondAnswer());
            answers.add(eq.getThirdAnswer());
            answers.add(eq.getFourthAnswer());
        }

        int correct = 0;
        for (String s : history.getExamAnswers().split(",")) {
            if (answers.contains(s)) {
                correct+=1;
            }
        }

        history.setResult((correct  * 100) / examQuestions.size() + "%");
        history.setDate(new Date());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof User) {
            username = ((User)principal).getEmail();
        } else {
            username = principal.toString();
        }
        User user = this.userService.getByEmail(username);

        history.setUser(user);
        this.examHistoryRepository.save(history);

        ModelAndView modelAndView = new ModelAndView("testExam/takeExam");
        modelAndView.addObject("success", "You have submitted test - " + correct + " correct answers");
        modelAndView.addObject("questions", examQuestions);
        modelAndView.addObject("item", history);
        return modelAndView;
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

    @RequestMapping("/takeExamOpen/{id}")
    public ModelAndView takeExamOpen(@PathVariable("id") Long id) {
        TestExam testExam = testExamService.getTestExamById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (testExam != null) {
            List<ExamQuestion> examQuestions = examQuestionService.findAllByTest(testExam);
            if (!examQuestions.isEmpty()) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username;
                if (principal instanceof User) {
                     username = ((User)principal).getEmail();
                } else {
                    username = principal.toString();
                }
                ExamHistory examHistory = new ExamHistory();
                examHistory.setTestExam(testExam);
                examHistory.setUser(userService.getByEmail(username));
                examHistory.setExamAnswers("");
                modelAndView.addObject("questions", examQuestions);
                modelAndView.addObject("item", examHistory);
                modelAndView.setViewName("testExam/takeExam");
                return modelAndView;
            }
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
