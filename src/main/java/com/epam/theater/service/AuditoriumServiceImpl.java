package com.epam.theater.service;

import com.epam.theater.entity.Auditorium;
import com.epam.theater.repository.AuditoriumRepo;
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
public class AuditoriumServiceImpl implements AuditoriumService {
    private final AuditoriumRepo auditoriumRepo;

    @Autowired
    public AuditoriumServiceImpl(AuditoriumRepo auditoriumRepo) {
        this.auditoriumRepo = auditoriumRepo;
    }

    @Override
    public List<Auditorium> findAll() {
        return auditoriumRepo.findAll();
    }

    @Override
    public Page<Auditorium> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Auditorium findById(Long id) {
        return auditoriumRepo.getOne(id);
    }

    @Override
    public void delete(Long id) {
        auditoriumRepo.deleteById(id);
    }

    @Override
    public void save(Auditorium auditorium) {
        auditoriumRepo.save(auditorium);
    }

    @Override
    public void update(Auditorium auditorium) {
        auditoriumRepo.saveAndFlush(auditorium);
    }

    @Override
    public void saveList(List<Auditorium> elementList) {
        auditoriumRepo.saveAll(elementList);
    }

    @Override
    public Auditorium getByName(Long number) {
        return auditoriumRepo.getByNumberOfAuditorium(number);
    }
}
