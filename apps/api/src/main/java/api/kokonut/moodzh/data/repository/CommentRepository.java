package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {}
