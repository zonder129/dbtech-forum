package park.server.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import park.server.DAO.ForumDAO;
import park.server.model.Forum;
import park.server.model.Thread;
import park.server.model.User;
import park.server.queries.ForumQueries;
import park.server.queries.ForumVisitorsQueries;
import park.server.rowmappers.ForumRowMapper;
import park.server.rowmappers.ThreadRowMapper;
import park.server.rowmappers.UserRowMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumService implements ForumDAO {

    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Forum> forumRowMapper = new ForumRowMapper();
    private static final RowMapper<Thread> threadRowMapper = new ThreadRowMapper();
    private static final RowMapper<User> userRowMapper = new UserRowMapper();

    public ForumService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Forum createForum(Forum newForum) {
        jdbcTemplate.update(ForumQueries.CreateForumByUserQuery, newForum.getSlug(), newForum.getTitle(), newForum.getUser(), newForum.getUser());
        return getForumBySlug(newForum.getSlug());
    }

    @Override
    public Forum getForumBySlug(String slug) {
        return jdbcTemplate.queryForObject(ForumQueries.GetForumBySlugQuery, forumRowMapper, slug);
    }

    @Override
    public List<Thread> getForumThreads(String slug, Integer limit, String since, Boolean desc) {
        final List<Object> keys = new ArrayList<>();
        keys.add(slug);
        if (since != null) {
            keys.add(since);
        }
        if (limit != null) {
            keys.add(limit);
        }
        return jdbcTemplate.query(ForumQueries.GetThreadsQuery(limit, since, desc),
                keys.toArray(), threadRowMapper);
    }

    @Override
    public List<User> getForumUsers(String slug, Integer limit, String since, Boolean desc) {
        final Integer forumID = jdbcTemplate.queryForObject(ForumQueries.GetForumIdBySlugQuery,
                Integer.class, slug);
        return jdbcTemplate.query(ForumQueries.GetUsersQuery(limit, since, desc),
                userRowMapper, forumID);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(ForumQueries.DeleteAllForumQuery);
    }

    @Override
    public void deleteAllVisitors() {
        jdbcTemplate.update(ForumVisitorsQueries.DeleteAllForumVisitorsQuery);
    }

    @Override
    public Integer amount() {
        return jdbcTemplate.queryForObject(ForumQueries.GetStatusQuery, Integer.class);
    }


}
