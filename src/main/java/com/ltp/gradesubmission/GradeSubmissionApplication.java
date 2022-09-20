package com.ltp.gradesubmission;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@AllArgsConstructor
public class GradeSubmissionApplication implements CommandLineRunner {

	private StudentRepository studentRepository;
	private CourseRepository courseRepository;
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		studentRepository.saveAll(Arrays.asList(
				new Student("Harry Potter", LocalDate.parse(("1980-07-31"))),
				new Student("Ron Weasley", LocalDate.parse(("1980-03-01"))),
				new Student("Hermione Granger", LocalDate.parse(("1979-09-19"))),
				new Student("Neville Longbottom", LocalDate.parse(("1980-07-30")))
		));
		courseRepository.saveAll(Arrays.asList(new Course("Charms", "CH104",
				"In this class, you will learn spells concerned with giving an object new and unexpected properties."),
				new Course("Defence Against the Dark Arts", "DADA",
						"In this class, you will learn defensive techniques against the dark arts."),
				new Course("Herbology", "HB311",
						"In this class, you will learn the study of magical plants and how to take care of, utilize and combat them."),
				new Course("History of Magic", "HIS393",
						"In this class, you will learn about significant events in wizarding history."),
				new Course("Potions", "POT102",
						"In this class, you will learn correct mixing and stirring of ingredients to create mixtures with magical effects."),
				new Course("Transfiguration", "TR442",
						"In this class, you will learn the art of changing the form or appearance of an object.")
		));
		userService.saveUser(new User("admin","admin-pass"));
		userService.saveUser(new User("user","user-pass"));
	}
}
