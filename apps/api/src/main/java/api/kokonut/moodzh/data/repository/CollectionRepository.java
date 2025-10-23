package api.kokonut.moodzh.data.repository;

import api.kokonut.moodzh.data.model.Collections;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collections, String> {
    List<Collections> findByUserId(String userId);

}
