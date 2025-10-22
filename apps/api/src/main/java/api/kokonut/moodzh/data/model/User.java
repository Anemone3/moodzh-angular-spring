package api.kokonut.moodzh.data.model;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import api.kokonut.moodzh.data.embeddable.LinkSocial;
import api.kokonut.moodzh.data.embeddable.LocationUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,length = 20, unique = true)
    private String username;

    @Column(nullable = false,length = 100)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = Boolean.TRUE;

    @Embedded
    private LocationUser location;

    @Embedded
    private LinkSocial socialLinks;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Images> images = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Collections> collections = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Comments> comments = new HashSet<>();

    @OneToMany(mappedBy = "emisor", fetch = FetchType.LAZY)
    private Set<Notification> sentNotifications = new HashSet<>();

    @OneToMany(mappedBy = "receptor", fetch = FetchType.LAZY)
    private Set<Notification> receivedNotifications = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
