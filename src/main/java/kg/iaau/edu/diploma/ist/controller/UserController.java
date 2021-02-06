package kg.iaau.edu.diploma.ist.controller;

import kg.iaau.edu.diploma.ist.entity.Role;
import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.repository.RoleRepository;
import kg.iaau.edu.diploma.ist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/register")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/register/save")
    public String save(@ModelAttribute  User user, BindingResult bindingResult) {
        if (user.getId() == null) {
            user.setAdmin(false);
            user.setDate(new Date());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        this.userService.save(user);
        return "redirect:/";
    }
}
