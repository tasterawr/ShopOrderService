package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Status;

import java.util.List;

public interface StatusService {
    Status saveStatus(Status status);

    Status getById(Long id);

    List<Status> getAll();

    Status updateStatus(Status status);

    void deleteById(Long id);
}
