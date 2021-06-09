package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Role;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.model.UserPattern;
import kg.iaau.edu.diploma.ist.pagination.Pager;
import kg.iaau.edu.diploma.ist.pagination.PaginationConstant;
import kg.iaau.edu.diploma.ist.repository.RoleRepository;
import kg.iaau.edu.diploma.ist.service.DepartmentService;
import kg.iaau.edu.diploma.ist.service.UserService;
import kg.iaau.edu.diploma.ist.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kg.iaau.edu.diploma.ist.pagination.PaginationConstant.BUTTONS_TO_SHOW;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DepartmentService departmentService;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DepartmentService departmentService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.departmentService = departmentService;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView getHardwarePage(@RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size){
        return getModelAndView("user/index", new UserPattern(), page, size);
    }

    @RequestMapping("/get/{id}")
    public ModelAndView showHotel(Model model, @PathVariable("id") Long id) {
        User news = userService.getUserById(id);
        model.addAttribute("user", news);
        return new ModelAndView("user/detail");
    }

    @RequestMapping("/create")
    public ModelAndView addToHard(){
        ModelAndView modelAndView = new ModelAndView("user/edit");
        modelAndView.addObject("item", new User());
        modelAndView.addObject("departments", departmentService.getAllActive());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute  User user, BindingResult bindingResult) {
        Role userRole;
        if (user.getId() == null) {
            user.setDate(new Date());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        if (user.getAdmin() != null && user.getAdmin()) {
            userRole = roleRepository.findByRole("ADMIN");
        } else userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        this.userService.save(user);
        return "redirect:/user/index";
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchFaculty(@ModelAttribute UserPattern pattern, BindingResult br,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size){
        return getModelAndView("user/index", pattern, page, size);
    }

    @RequestMapping(value = "/list")
    public ModelAndView listFaculty(
            @ModelAttribute UserPattern pattern, BindingResult br,
            @RequestParam("firstName") String fname,
            @RequestParam("lastName") String lname,
            @RequestParam("email") String email,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        pattern.setFirstName(fname);
        pattern.setLastName(lname);
        pattern.setEmail(email);
        return getModelAndView("user/index", pattern, page, size);
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteFaculty(@PathVariable("id") Long id) {
        User news = userService.getUserById(id);
        if (news != null) {
            userService.delete(news);
        }
        return new ModelAndView("redirect:/user/index");
    }

    private ModelAndView getModelAndView(String view, UserPattern pattern, Optional<Integer> page, Optional<Integer> size){
        ModelAndView modelAndView = new ModelAndView(view);

        int currentPage = page.orElse(PaginationConstant.INITIAL_PAGE);
        int pageSize = size.orElse(PaginationConstant.INITIAL_PAGE_SIZE);

        Specification<User> specification = new UserSpecification(pattern);
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.Direction.DESC, "id");

        Page<User> users = userService.getAllUsers(specification, pageable);

        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        Pager pager = new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("selectedPageNumber", pageSize);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("pageSizes", PaginationConstant.PAGE_SIZE);
        modelAndView.addObject("users", users);
        modelAndView.addObject("pattern", pattern);
        return modelAndView;
    }
}
