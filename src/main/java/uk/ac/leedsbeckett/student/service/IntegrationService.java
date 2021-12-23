package uk.ac.leedsbeckett.student.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.YamlSourceFactory;
import uk.ac.leedsbeckett.student.model.Account;
import uk.ac.leedsbeckett.student.model.Invoice;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@PropertySource(value = "classpath:integrations.yml", factory = YamlSourceFactory.class)
public class IntegrationService {

    private final RestTemplate restTemplate;
    @Value("${student.create}")
    private String[] studentCreationSubscribers;
    @Value("${course.enrol}")
    private String courseEnrolmentSubscriber;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyStudentCreated(@NotNull Account account) {
        List<Account> responses = Stream.of(studentCreationSubscribers)
                .map(subscriber -> restTemplate.postForObject(subscriber, account, Account.class))
                .collect(Collectors.toList());
    }

    public void createCourseFeeInvoice(@NotNull Invoice invoice) {
        Invoice response = restTemplate.postForObject(courseEnrolmentSubscriber, invoice, Invoice.class);
        System.out.println(response);
    }
}
