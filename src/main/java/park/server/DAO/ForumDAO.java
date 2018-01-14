package park.server.DAO;

import park.server.model.Forum;
import park.server.model.Thread;
import park.server.model.User;

import java.util.List;

public interface ForumDAO {
    Forum createForum(Forum newForum);

    Forum getForumBySlug(String slug);

    List<Thread> getForumThreads(String slug, Integer limit, String since, Boolean desc);

    List<User> getForumUsers (String slug, Integer limit, String since, Boolean desc);

    void deleteAll();

    void deleteAllVisitors();

    Integer amount();
}
