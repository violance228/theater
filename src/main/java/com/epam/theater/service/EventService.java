package com.epam.theater.service;

import com.epam.theater.entity.Event;

import java.util.Date;
import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */


public interface EventService extends BaseMethods<Event> {
    Event getByNameEvent(String name);
    List<Event> getNextEvents(Date to);
}
