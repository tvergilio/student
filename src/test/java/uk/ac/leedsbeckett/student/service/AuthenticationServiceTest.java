package uk.ac.leedsbeckett.student.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import uk.ac.leedsbeckett.student.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AuthenticationServiceTest extends AuthenticationServiceTestBase {

    @Test
    void testShowLoginError_returnsCorrectView() {
        Model model = new ExtendedModelMap();
        String result = authenticationService.showLoginError(model);
        assertEquals("login", result);
        assertNotNull(model.getAttribute("loginError"));
        assertTrue((Boolean) model.getAttribute("loginError"));
    }

    @Test
    void testShowRegistrationForm_returnsCorrectView() {
        Model model = new ExtendedModelMap();
        String result = authenticationService.showRegistrationForm(model);
        assertEquals("register", result);
        assertNotNull(model.getAttribute("user"));
        assertTrue(model.getAttribute("user") instanceof User);
    }

    @Test
    void testRegisterNewUser_createsUser_andReturnsCorrectView() {
        Model model = new ExtendedModelMap();
        String result = authenticationService.registerNewUser(user);
        assertEquals("register-success", result);
        verify(userRepository, times(1)).save(user);
    }

}