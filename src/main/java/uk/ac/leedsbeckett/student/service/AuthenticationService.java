package uk.ac.leedsbeckett.student.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uk.ac.leedsbeckett.student.model.Role;
import uk.ac.leedsbeckett.student.model.User;
import uk.ac.leedsbeckett.student.model.UserRepository;

@Component
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String showLoginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    public String registerNewUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.STUDENT);
        userRepository.save(user);
        return "register-success";
    }
}
