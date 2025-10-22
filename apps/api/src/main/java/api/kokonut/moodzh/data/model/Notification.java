package api.kokonut.moodzh.data.model;

import java.time.Instant;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import api.kokonut.moodzh.data.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emisor_id" , nullable = true)
    private User emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    private User receptor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "is_read", nullable = false, columnDefinition = "boolean default false")
    private boolean isRead = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "target_post_id",nullable = true)
    private Post post;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
