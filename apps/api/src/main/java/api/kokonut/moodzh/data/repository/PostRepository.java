package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
