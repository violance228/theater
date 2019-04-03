package com.epam.theater.repository;

import com.epam.theater.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */


public interface OrderRepo extends JpaRepository<Order, Long> {
}
