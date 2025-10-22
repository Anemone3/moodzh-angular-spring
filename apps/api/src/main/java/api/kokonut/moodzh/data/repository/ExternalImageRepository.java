package api.kokonut.moodzh.data.repository;

import api.kokonut.moodzh.data.model.ExternalImages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalImageRepository extends JpaRepository<ExternalImages, String> {}
