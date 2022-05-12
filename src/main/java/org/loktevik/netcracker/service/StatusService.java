package org.loktevik.netcracker.service;

import org.loktevik.netcracker.domain.Status;

import java.util.List;

/**
 * Service interface for Status model.
 */
public interface StatusService {

    /**
     * Finds status in database by name.
     * @param name name of status.
     * @return instance of status with specified name.
     */
    Status getByName(String name);

    /**
     * Saves new or updates existing status in the database.
     * @param status instance of new or existing status.
     * @return saved or updated status.
     */
    Status saveStatus(Status status);

    /**
     * Finds status in database by id.
     * @param id id of status.
     * @return instance of status with specified id.
     */
    Status getById(Long id);

    /**
     * Finds all statuses in the database.
     * @return all statuses from the database.
     */
    List<Status> getAll();

    /**
     * Updates existing status.
     * @param status instance of status to update.
     * @return updated status.
     */
    Status updateStatus(Status status);

    /**
     * Deletes status with specified id.
     * @param id id of status.
     */
    void deleteById(Long id);
}
