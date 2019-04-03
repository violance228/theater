package com.epam.theater.repository;

import com.epam.theater.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
}
