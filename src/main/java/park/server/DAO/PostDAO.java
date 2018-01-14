package park.server.DAO;

import park.server.model.Post;
import park.server.model.PostFull;
import park.server.model.PostUpdate;

import java.sql.SQLException;
import java.util.List;

public interface PostDAO {
    List<Post> createPosts(String slugOrId, List<Post> newPosts) throws SQLException;

    PostFull getFullPostById(Integer id, List<String> related);

    Post updatePost(Integer id, PostUpdate postUpdate);

    void deleteAll();

    Integer amount();
}
