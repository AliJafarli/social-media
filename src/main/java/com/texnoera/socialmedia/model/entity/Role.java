package com.texnoera.socialmedia.model.entity;

import com.texnoera.socialmedia.security.enums.SocialMediaUserRole;
import com.texnoera.socialmedia.utils.UserRoleTypeConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_system_role", nullable = false, updatable = false)
    @Convert(converter = UserRoleTypeConverter.class)
    private SocialMediaUserRole userSystemRole;

    @Column()
    private boolean active;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();

}
