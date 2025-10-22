package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
