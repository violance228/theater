package com.epam.theater.service;

import com.epam.theater.entity.Event;
import com.epam.theater.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Service
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public List<Event> findAll() {
        return eventRepo.findAll();
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Event findById(Long id) {
        return eventRepo.getOne(id);
    }

    @Override
    public void delete(Long id) {
        eventRepo.deleteById(id);
    }

    @Override
    public void save(Event event) {
        eventRepo.save(event);
    }

    @Override
    public void update(Event event) {
        eventRepo.saveAndFlush(event);
    }

    @Override
    public void saveList(List<Event> elementList) {
        eventRepo.saveAll(elementList);
    }

    @Override
    public Event getByNameEvent(String name) {
        return eventRepo.getEventByName(name);
    }

    @Override
    public List<Event> getNextEvents(Date to) {
        return eventRepo.getAllByDateFromAfterOrderByDateFrom(to);
    }
}
