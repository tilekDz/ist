package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public void delete(User user) {
        user.setActive(false);
        this.userRepository.save(user);
    }

    public List<User> getAllActive() {
        return this.userRepository.findAllByActiveIsTrue();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmailAndActiveIsTrue(email);
    }

    public Page<User> getAllUsers(Specification specification, Pageable pageable) {
        return this.userRepository.findAll(specification, pageable);
    }
}
