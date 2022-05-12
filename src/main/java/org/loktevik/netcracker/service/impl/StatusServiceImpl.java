package org.loktevik.netcracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.loktevik.netcracker.domain.Status;
import org.loktevik.netcracker.repository.StatusRepository;
import org.loktevik.netcracker.service.StatusService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service implementation class for Status model. Implements methods from StatusService.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final Logger log = Logger.getLogger(StatusServiceImpl.class);
    private final StatusRepository statusRepo;

    @Override
    public Status getByName(String name) {
        return statusRepo.findByName(name);
    }

    @Override
    public Status saveStatus(Status status) {
        log.info(new FormattedMessage("Saving new status \"{}\".", status.getName()));
        return statusRepo.save(status);
    }

    @Override
    public Status getById(Long id) {
        log.info(new FormattedMessage("Getting status with id: {}", id));
        return statusRepo.getById(id);
    }

    @Override
    public List<Status> getAll() {
        log.info("Getting all statuses.");
        return statusRepo.findAll();
    }

    @Override
    public Status updateStatus(Status status) {
        log.info(new FormattedMessage("Updating status with id: {}", status.getId()));
        return statusRepo.save(status);
    }

    @Override
    public void deleteById(Long id) {
        log.info(new FormattedMessage("Deleting status with id: {}", id));
        statusRepo.deleteById(id);
    }
}
