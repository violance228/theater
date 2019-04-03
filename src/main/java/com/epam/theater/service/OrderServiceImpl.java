package com.epam.theater.service;

import com.epam.theater.entity.Order;
import com.epam.theater.repository.OrderRepo;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.getOne(id);
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public void save(Order order) {
        orderRepo.save(order);
    }

    @Override
    public void update(Order order) {
        orderRepo.saveAndFlush(order);
    }

    @Override
    public void saveList(List<Order> elementList) {
        orderRepo.saveAll(elementList);
    }
}
