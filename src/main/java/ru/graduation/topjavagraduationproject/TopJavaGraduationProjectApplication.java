package ru.graduation.topjavagraduationproject;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.graduation.topjavagraduationproject.repository.UserRepository;


@SpringBootApplication
@AllArgsConstructor
public class TopJavaGraduationProjectApplication implements ApplicationRunner {
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TopJavaGraduationProjectApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(userRepository.findByLastNameContainingIgnoreCase("last"));
    }
}