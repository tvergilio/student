package uk.ac.leedsbeckett.student.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.*;

@Component
public class PortalService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public PortalService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new PortalUserDetails(user);
    }

    public ModelAndView renderHomePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication== null || !authentication.isAuthenticated()) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView("home");
        User user = userRepository.findUserByUserName(authentication.getName());
        modelAndView.addObject("user", user);
        Student student = studentRepository.findByUserId(user.getId());
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    private User findUser(Model model, Authentication authentication) {
        return userRepository.findUserByUserName(authentication.getName());
    }

    private void findStudent(Model model, User user) {
        Student student = studentRepository.findByUserId(user.getId());
        model.addAttribute(student);
    }
}