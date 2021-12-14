package uk.ac.leedsbeckett.student.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.ac.leedsbeckett.student.model.PortalUserDetails;
import uk.ac.leedsbeckett.student.model.User;
import uk.ac.leedsbeckett.student.model.UserRepository;

public class PortalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PortalUserDetailsService(UserRepository userRepository) {
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

}