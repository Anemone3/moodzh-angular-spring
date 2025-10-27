package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
