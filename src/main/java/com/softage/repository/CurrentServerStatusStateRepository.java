package com.softage.repository;

import com.softage.domain.ServerStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentServerStatusStateRepository extends MongoRepository<ServerStatus, String> {
}
