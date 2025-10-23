package api.kokonut.moodzh.core.services.post;


public interface PostService {

    void createCommentPost(String postId, String commentText);

    void postExists(String postId);

    void getDetailPostImage(String postId);

    void savePostImageLocalOrExternal(String postId, String imageUrl);
}
