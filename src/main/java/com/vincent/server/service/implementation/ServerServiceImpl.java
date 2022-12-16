package com.vincent.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vincent.server.enumeration.Status;
import com.vincent.server.model.Server;
import com.vincent.server.repo.ServerRepo;
import com.vincent.server.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
	
//	@Autowired(instead we use constructor injection by lombok (@requiredargsconstructor))
	private final ServerRepo serverRepo;

	@Override
	public Server create(Server server) {
		log.info("Saving a new server : {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("Pinging server IP : {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepo.save(server);
		return server;
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching server by id : {}", id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server : {}", server.getName());
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by ID : {}", id);
		serverRepo.deleteById(id);
		return true;
	}
	
	private String setServerImageUrl() {
		String[] imageNames = {"server1", "server2", "server3", "server4"};
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
	}
}
