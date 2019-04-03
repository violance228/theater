package com.epam.theater.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Entity
@Table(name = "events")
@Data
@Getter
@Setter
@EqualsAndHashCode
public class Event {
    @Id
    @Column(name = "event_id")
    @SequenceGenerator(name = "auditorium_seq", sequenceName = "auditorium_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "event_rating")
    private EventRating eventRating;

    @OneToMany(mappedBy = "event")
    Set<Ticket> tickets;

    @ManyToMany
    @JoinTable(
            name = "event_auditoriums",
            joinColumns = @JoinColumn(
                    name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "auditorium_id", referencedColumnName = "auditorium_id"))
    private Set<Auditorium> auditoriums;
}
