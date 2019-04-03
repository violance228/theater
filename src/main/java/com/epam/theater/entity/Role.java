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
@Table(name = "Roles")
@Data
@Getter
@Setter
@EqualsAndHashCode
public class Role {
    @Id
    @Column(name = "role_id")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    private Long id;

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_altername")
    private String altername;

    @Column(name = "role_target")
    private String target;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
