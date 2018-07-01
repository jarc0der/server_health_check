package com.softage.repository;

import com.softage.domain.ServerStatus;

import java.util.List;

public interface CurrentServiceStateRepository {

    List<ServerStatus> findAll();

    ServerStatus findById(int id);

}
