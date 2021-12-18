package uk.ac.leedsbeckett.student;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.ac.leedsbeckett.student.controller.StudentInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final StudentInterceptor courseInterceptor;

    public MvcConfig(StudentInterceptor courseInterceptor) {
        this.courseInterceptor = courseInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/profile").setViewName("profile");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(courseInterceptor).addPathPatterns("/courses/**", "/home", "/enrolments/**", "/profile/**");
    }
}