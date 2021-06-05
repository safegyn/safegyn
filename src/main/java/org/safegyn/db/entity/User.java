package org.safegyn.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.safegyn.config.security.UserRole;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(name = "user_username_index", columnList = "username")})
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordDigest;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.ADMIN;

}
