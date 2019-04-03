package com.epam.theater.service;

import com.epam.theater.entity.Ticket;
import com.epam.theater.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketServiceImpl(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepo.getOne(id);
    }

    @Override
    public void delete(Long id) {
        ticketRepo.deleteById(id);
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketRepo.saveAndFlush(ticket);
    }

    @Override
    public void saveList(List<Ticket> elementList) {
        ticketRepo.saveAll(elementList);
    }
}
