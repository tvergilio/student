package uk.ac.leedsbeckett.student.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.ac.leedsbeckett.student.model.*;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

public class AuthenticationServiceTestBase {

    @MockBean
    protected UserRepository userRepository;
    @Autowired
    protected AuthenticationService authenticationService;

    protected User user;

    @BeforeEach
    public void setUp() {
        createTestObjects();
        defineMockingBehaviour();
    }

    protected void createTestObjects() {
        Faker faker = new Faker();
        user = new User(faker.name().username(), Role.STUDENT, "user@gmail.com", faker.animal().name());
        user.setId(1L);
    }

    protected void defineMockingBehaviour() {
        Mockito.when(userRepository.save(any(User.class)))
                .then(returnsFirstArg());
    }

    @AfterEach
    public void tearDown() {
        user = null;
        userRepository = null;
        authenticationService = null;
    }
}
