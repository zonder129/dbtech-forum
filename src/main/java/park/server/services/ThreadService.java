package park.server.services;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import park.server.DAO.ThreadDAO;
import park.server.model.Post;
import park.server.model.ThreadUpdate;
import park.server.model.Vote;
import park.server.queries.*;
import park.server.rowmappers.PostRowMapper;
import park.server.rowmappers.ThreadRowMapper;
import park.server.model.Thread;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Service
public class ThreadService implements ThreadDAO {

    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Thread> threadRowMapper = new ThreadRowMapper();

    private static final RowMapper<Post> postRowMapper = new PostRowMapper();

    public ThreadService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Thread createThread(@NotNull String slug, @NotNull Thread thread) {
        Integer id = jdbcTemplate.queryForObject(ThreadQueries.CreateNewThreadQuery, Integer.class, thread.getAuthor(), thread.getAuthor(), thread.getCreated(), slug,
                slug, thread.getMessage(), thread.getSlug(), thread.getTitle());
        jdbcTemplate.update(ThreadQueries.UpdateThreadsCountQuery, 1, thread.getForum());

        Integer userID = jdbcTemplate.queryForObject(UserQueries.GetUserIdByNicknameQuery, Integer.class, thread.getAuthor());

        Integer forumID = jdbcTemplate.queryForObject(ForumQueries.GetForumIdBySlugQuery, Integer.class, slug);

        jdbcTemplate.update(ForumVisitorsQueries.AddNewVisitorQuery, userID, forumID);

        return jdbcTemplate.queryForObject(ThreadQueries.GetThreadByIdQuery, threadRowMapper, id);

    }

    @Override
    public Thread getThreadBySlug(String slug) {
        return jdbcTemplate.queryForObject(ThreadQueries.GetThreadBySlugQuery, threadRowMapper, slug);
    }

    @Override
    public Thread getThreadBySlugOrId(String slugOrId) {
        if (slugOrId.matches("\\d+")) {
            return jdbcTemplate.queryForObject(ThreadQueries.GetThreadByIdQuery,
                    threadRowMapper, Integer.valueOf(slugOrId));
        }
        return jdbcTemplate.queryForObject(ThreadQueries.GetThreadBySlugQuery,
                threadRowMapper, slugOrId);
    }

    @Override
    public Thread setVote(String slugOrId, Vote vote) {

        Thread thread = getThreadBySlugOrId(slugOrId);
        Integer user_id = jdbcTemplate.queryForObject(UserQueries.GetUserIdByNicknameQuery, Integer.class, vote.getNickname());

        try {
            Integer previous_vote = jdbcTemplate.queryForObject(ThreadQueries.CheckPreviousVoteQuery, Integer.class, user_id,
                    thread.getId());
            if (previous_vote.equals(vote.getVoice())) {
                return thread;
            }
            jdbcTemplate.update(ThreadQueries.UpdateVoteQuery, vote.getVoice(), user_id, thread.getId());

            jdbcTemplate.update(ThreadQueries.UpdateThreadVotesQuery, 2 * vote.getVoice(), thread.getId());
            thread.setVotes(thread.getVotes() + 2 * vote.getVoice());

        } catch(IncorrectResultSizeDataAccessException exception) {
            jdbcTemplate.update(ThreadQueries.AddVoteQuery, user_id, thread.getId(), vote.getVoice());

            jdbcTemplate.update(ThreadQueries.UpdateThreadVotesQuery, vote.getVoice(), thread.getId());
            thread.setVotes(thread.getVotes() + vote.getVoice());
        }

        return thread;
    }

    @Override
    public Thread updateThread(String slugOrId, ThreadUpdate threadUpdate) {
        final Thread threadFromDB = getThreadBySlugOrId(slugOrId);

        Integer notNullFields = 0;

        if (threadUpdate.getMessage() != null) {
            ++notNullFields;
            threadFromDB.setMessage(threadUpdate.getMessage());
        }
        if (threadUpdate.getTitle() != null) {
            ++notNullFields;
            threadFromDB.setTitle(threadUpdate.getTitle());
        }

        if (notNullFields > 0) {
            jdbcTemplate.update(ThreadQueries.UpdateThreadQuery, threadFromDB.getTitle(), threadFromDB.getMessage(), threadFromDB.getId());
        }

        return threadFromDB;
    }

    @Override
    public List<Post> getThreadPosts(String slugOrId, Integer limit, Integer since, String sort, Boolean desc) {
        final Thread requestedThread = getThreadBySlugOrId(slugOrId);

        final List<Object> keys = new ArrayList<>();
        keys.add(requestedThread.getId());
        if (since != null) {
            keys.add(since);
        }
        if (limit != null) {
            keys.add(limit);
        }
        if (sort == null) {
            return jdbcTemplate.query(ThreadQueries.GetThreadPostsInFlatOrder(limit, since, desc),
                    keys.toArray(), postRowMapper);
        }
        switch (sort) {
            case "flat":
                return jdbcTemplate.query(ThreadQueries.GetThreadPostsInFlatOrder(limit, since, desc), keys.toArray(), postRowMapper);
            case "tree" :
                return jdbcTemplate.query(ThreadQueries.GetThreadPostsInTreeOrder(limit,since,desc), keys.toArray(), postRowMapper);
            case "parent_tree" :
                return jdbcTemplate.query(ThreadQueries.GetThreadPostsInParentTreeOrder(limit, since, desc), keys.toArray(), postRowMapper);
            default:
                break;
        }
        return jdbcTemplate.query(ThreadQueries.GetThreadPostsInFlatOrder(limit, since, desc), keys.toArray(), postRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(ThreadQueries.DeleteAllThreadsQuery);
    }

    @Override
    public Integer amount() {
        return jdbcTemplate.queryForObject(ThreadQueries.GetThreadsStatusQuery, Integer.class);
    }

}
