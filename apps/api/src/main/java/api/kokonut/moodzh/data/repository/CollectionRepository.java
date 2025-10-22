package api.kokonut.moodzh.data.repository;

import api.kokonut.moodzh.data.model.Collections;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collections, String> {}
