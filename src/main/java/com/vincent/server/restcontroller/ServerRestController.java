package com.vincent.server.restcontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vincent.server.enumeration.Status;
import com.vincent.server.model.Response;
import com.vincent.server.model.Server;
import com.vincent.server.service.ServerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerRestController {
	private final ServerService serverService;
	
	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(Map.of("servers", serverService.list(10)))
				.message("Servers retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(Map.of("server", server))
				.message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping fails")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(Map.of("server", serverService.create(server)))
				.message("Server created")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
		);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(Map.of("server", serverService.get(id)))
				.message("Server retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(LocalDateTime.now())
				.data(Map.of("deleted", serverService.delete(id)))
				.message("Server deleted")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
		);
	}
	
	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
//	@GetMapping(path = "/image/{fileName}", produces = MediaType.ALL_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
		if(fileName.contains(".jpeg")) {
			fileName = fileName.replace(".jpeg", ".jpg");
		}
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
	}
}
