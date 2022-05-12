package org.loktevik.netcracker.repository;

import org.loktevik.netcracker.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String name);

    Status getById(Long id);

    List<Status> findAll();

    Status save(Status status);

    void deleteById(Long id);

    void deleteByName(String name);
}
