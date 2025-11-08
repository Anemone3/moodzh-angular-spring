package api.kokonut.moodzh.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.Images;

public interface ImageRepository extends JpaRepository<Images, String> {
    List<Images> findAllByUserId(String userId);
}
