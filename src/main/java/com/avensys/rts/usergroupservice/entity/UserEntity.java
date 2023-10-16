package com.avensys.rts.usergroupservice.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="last_login_datetime")
    private LocalDateTime lastLoginDatetime;

    @Column(name="contact_id")
    private Integer contactId;

    @Column(name="entity_id")
    private Integer entityId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
