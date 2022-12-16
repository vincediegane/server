package com.vincent.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vincent.server.model.Server;

@Repository
public interface ServerRepo extends JpaRepository<Server, Long>{
	Server findByIpAddress(String ipAddress);
}
