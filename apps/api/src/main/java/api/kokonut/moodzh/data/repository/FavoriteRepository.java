package api.kokonut.moodzh.data.repository;

import api.kokonut.moodzh.data.model.Favorites;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorites, Long> {}
