package park.server.DAO;

import park.server.model.Post;
import park.server.model.Thread;
import park.server.model.ThreadUpdate;
import park.server.model.Vote;

import java.util.List;

public interface ThreadDAO {
    Thread createThread(String slug, Thread thread);

    Thread getThreadBySlug(String slug);

    Thread getThreadBySlugOrId(String slugOrId);

    Thread setVote(String slugOrId, Vote vote);

    Thread updateThread(String slugOrId, ThreadUpdate threadUpdate);

    List<Post> getThreadPosts(String slugOrId, Integer limit, Integer since, String sort, Boolean desc);

    void deleteAll();

    Integer amount();

}
