package api.kokonut.moodzh.core.services.collections;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import api.kokonut.moodzh.api.exceptions.http.ResourceExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.kokonut.moodzh.api.dto.request.CollectionRequest;
import api.kokonut.moodzh.api.dto.response.CollectionResponse;
import api.kokonut.moodzh.api.dto.response.ImagesResponse;
import api.kokonut.moodzh.api.exceptions.http.ResourceNotFoundException;
import api.kokonut.moodzh.core.strategy.contenttype.ContentTypeFactory;
import api.kokonut.moodzh.core.strategy.contenttype.IContentTypeStrategy;
import api.kokonut.moodzh.data.model.Collections;
import api.kokonut.moodzh.data.model.Post;
import api.kokonut.moodzh.data.repository.CollectionRepository;
import api.kokonut.moodzh.data.repository.UserRepository;
import api.kokonut.moodzh.util.mapper.CollectionMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class CollectionServiceImpl implements CollectionService {

	private final CollectionRepository collectionRepository;
	private final UserRepository userRepository;
	private final ContentTypeFactory contentTypeFactory;
	private final CollectionMapper collectionMapper;

	@Override
	public List<CollectionResponse> getCollections(Pageable pageable) {
		collectionRepository.findAll(pageable);
		throw new UnsupportedOperationException("Unimplemented method 'getCollectionById'");

	}

	@Override
	public List<CollectionResponse> getUserCollections(String userId) {
		List<Collections> collectionsUser = collectionRepository.findByUserId(userId);
		if (collectionsUser.isEmpty()) {
			/* regresamos un array vacio */
			return List.of();
		}
		log.info("Get collections by user id: {}", userId);
		return collectionsUser.stream().map(this::getCollectionWithImages).toList();
	}

	@Override
	public CollectionResponse getCollectionById(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCollectionById'");
	}

	@Override
	@Transactional
	public CollectionResponse createCollection(CollectionRequest request, String userId) {
		var collectionsUser = collectionRepository.findByUserId(userId);
		String collectionRequestName = request.name();
		boolean nameAlreadyExistsInUserCollections = collectionsUser.stream()
				.noneMatch(collection -> collection.getName().equals(collectionRequestName));

		if (nameAlreadyExistsInUserCollections) {
			/* Crear una excepcion de nombre ya existente */
			throw new ResourceExistsException("Ya existe una colección con ese nombre");
		}

		var currentUser = userRepository.getReferenceById(userId);

		Collections newCollection = Collections.builder()
				.name(request.name())
				.description(request.description().isEmpty() ? null : request.description())
				.user(currentUser)
				.build();

		log.info("Create new collection: {}", newCollection);

		try {
			var collections = collectionRepository.save(newCollection);
			return getCollectionWithImages(collections);
		} catch (

		DataIntegrityViolationException e) {
			if (e.getMessage() != null && e.getMessage().contains("constrain") && e.getMessage().contains("user_id")) {
				throw new ResourceNotFoundException(
						"No se pudo crear la colección, el usuario no fue encontrado con el ID " + userId);
			}
			throw e;
		}

	}

	@Override
	public void updateCollection(String id, String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateCollection'");
	}

	@Override
	public void deleteCollection(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteCollection'");
	}

	@Override
	public void saveImageOnCollection(String id, String imageId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'saveImageOnCollection'");
	}

	@Override
	public void removeImageFromCollection(String id, String imageId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeImageFromCollection'");
	}

	private CollectionResponse getCollectionWithImages(Collections newCollection) {

		List<CompletableFuture<ImagesResponse>> futureImages = newCollection.getPostCollections().stream()
				.map(posts -> {
					Post postEntity = posts.getPost();
					IContentTypeStrategy strategyImpl = contentTypeFactory.get(postEntity.getContentType().name());
					return strategyImpl.getOneImageFromCore(postEntity);
				}).toList();
		CompletableFuture.allOf(futureImages.toArray(CompletableFuture[]::new)).join();
		List<ImagesResponse> images = futureImages.stream()
				.map(CompletableFuture::join) // extrae el resultado cuando ya esta completo
				.filter(Objects::nonNull) // para evitar nulos
				.toList();

		return collectionMapper.toResponse(newCollection, images);
	}

}
