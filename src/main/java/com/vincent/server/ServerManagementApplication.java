package com.vincent.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vincent.server.enumeration.Status;
import com.vincent.server.model.Server;
import com.vincent.server.repo.ServerRepo;

@SpringBootApplication
public class ServerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagementApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(ServerRepo serverRepo) {
		return args -> {
			serverRepo.save(new Server(null, "8.8.8.8", "Google", "16 GB", "Google server", "http://localhost:8080/server/image/server1.jpeg", Status.SERVER_UP));
		};
	}

}
