package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.User;

public interface UserRepository extends JpaRepository<User,String>{}
