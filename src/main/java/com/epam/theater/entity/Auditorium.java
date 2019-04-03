package com.epam.theater.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@Entity
@Table(name = "auditoriums")
@Data
@Getter
@Setter
@EqualsAndHashCode
public class Auditorium {
    @Id
    @Column(name = "auditorium_id")
    @SequenceGenerator(name = "auditorium_seq", sequenceName = "auditorium_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    private Long id;
    @Column(name = "number_of_auditorium")
    private Long numberOfAuditorium;
    @Column(name = "number_of_seats")
    private Long numberOfSeats;
    @Column(name = "number_of_vip_seats")
    private Long numberOfVipSeats;

    @OneToMany(mappedBy = "auditorium")
    Set<Ticket> tickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "auditoriums")
    private Set<Event> events;
}
