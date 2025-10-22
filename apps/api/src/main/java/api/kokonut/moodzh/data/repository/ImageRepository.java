package api.kokonut.moodzh.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.Images;

public interface ImageRepository extends JpaRepository<Images, String> {}
