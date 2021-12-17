package uk.ac.leedsbeckett.student.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
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

    public ModelAndView fetchStudentProfile(String view) {
        User user = getLoggedInUser();
        if (user == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("user", user);
        Student student = studentRepository.findByUserId(user.getId());
        if (student != null) {
            modelAndView.addObject("student", student);
        }
        return modelAndView;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            user = userRepository.findUserByUserName(authentication.getName());
        }
        return user;
    }
}