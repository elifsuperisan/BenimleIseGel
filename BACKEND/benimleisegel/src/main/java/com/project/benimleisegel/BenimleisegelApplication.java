package com.project.benimleisegel;

import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.repository.VehicleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BenimleisegelApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final VehicleRepository vehicleRepository;
	private final PasswordEncoder passwordEncoder;

	public BenimleisegelApplication(UserRepository userRepository,
									VehicleRepository vehicleRepository,
									PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.passwordEncoder = passwordEncoder;
    }

	//todo: ui ux geliştirmeleri -> toast message
	//todo: ?


	public static void main(String[] args) {
		SpringApplication.run(BenimleisegelApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		//user
		if (!userRepository.existsByEmail("muhammed@gmail.com")) {
			User user = new User();
			user.setEmail("muhammed@gmail.com");
			user.setPassword(passwordEncoder.encode("12345"));
			user.setFirstName("Muhammed");
			user.setLastName("Düzgün");
			user.setPhone("111222333");
			userRepository.save(user);
		}
	}
}
