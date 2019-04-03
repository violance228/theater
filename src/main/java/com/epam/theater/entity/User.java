package com.epam.theater.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
@Data
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private Long id;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "login")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "pib")
    private String PIB;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user")
    Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    @JsonIgnore
    private Set<Role> roles;
}
