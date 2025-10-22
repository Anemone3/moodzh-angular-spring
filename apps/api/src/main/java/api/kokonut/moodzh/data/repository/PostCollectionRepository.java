package api.kokonut.moodzh.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.kokonut.moodzh.data.embeddable.PostCollectionId;
import api.kokonut.moodzh.data.model.PostCollections;


public interface PostCollectionRepository extends JpaRepository<PostCollections, PostCollectionId> {

}
