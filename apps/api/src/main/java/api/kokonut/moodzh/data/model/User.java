package api.kokonut.moodzh.data.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import api.kokonut.moodzh.data.enums.ProviderAuth;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import api.kokonut.moodzh.data.embeddable.LinkSocial;
import api.kokonut.moodzh.data.embeddable.LocationUser;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false,length = 100,unique = true)
    private String email;


    @Column(nullable = true, length = 100)
    private String password;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "profile_url")
    private String profilePicture;

    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderAuth provider;

    @Embedded
    private LocationUser location;

    @Embedded
    private LinkSocial socialLinks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final Set<Images> images = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Collections> collections = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Comments> comments = new HashSet<>();

    @OneToMany(mappedBy = "emisor", fetch = FetchType.LAZY)
    private final Set<Notification> sentNotifications = new HashSet<>();

    @OneToMany(mappedBy = "receptor", fetch = FetchType.LAZY)
    private final Set<Notification> receivedNotifications = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
