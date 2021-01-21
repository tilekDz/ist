package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.User;
import kg.iaau.edu.diploma.ist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
