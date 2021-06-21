package com.danish.springbootdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student danish = new Student("Danish", "danish@gmail.com", LocalDate.of(1990, Month.JUNE, 12));
            Student daniyal = new Student("Alex", "alex@gmail.com", LocalDate.of(1992, Month.DECEMBER, 2));

            repository.saveAll(
                    List.of(danish, daniyal));
        };
    }
}
