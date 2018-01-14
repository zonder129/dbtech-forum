package park.server.services;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import park.server.DAO.PostDAO;
import park.server.model.*;
import park.server.model.Thread;
import park.server.queries.*;
import park.server.rowmappers.ForumRowMapper;
import park.server.rowmappers.PostRowMapper;
import park.server.rowmappers.ThreadRowMapper;
import park.server.rowmappers.UserRowMapper;

import java.sql.Array;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService implements PostDAO {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Post> postRowMapper = new PostRowMapper();
    private RowMapper<User> userRowMapper = new UserRowMapper();
    private RowMapper<Forum> forumRowMapper = new ForumRowMapper();
    private RowMapper<Thread> threadRowMapper = new ThreadRowMapper();

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> createPosts(String slugOrId, List<Post> newPosts) throws SQLException {

        final Integer threadID = slugOrId.matches("\\d+") ?
                jdbcTemplate.queryForObject(ThreadQueries.CheckThreadExistenceQuery, Integer.class, Integer.valueOf(slugOrId)) :
                jdbcTemplate.queryForObject(ThreadQueries.GetThreadIdBySlugQuery, Integer.class, slugOrId);

        if (newPosts.isEmpty()) {
            return newPosts;
        }

        Integer forumID = jdbcTemplate.queryForObject(ThreadQueries.GetForumIdBySlugOrIdQuery(slugOrId),
                Integer.class, slugOrId);
        String forumSlug = jdbcTemplate.queryForObject(ForumQueries.GetForumSlugByIdQuery, String.class, forumID);

        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        for (Post post : newPosts) {

            Integer postID = jdbcTemplate.queryForObject(PostQueries.GetNextIdQuery, Integer.class);
            post.setId(postID);
            post.setForum(forumSlug);
            post.setThread(threadID);
            post.setCreated(currentTime);

            Integer userID = jdbcTemplate.queryForObject(UserQueries.GetUserIdByNicknameQuery, Integer.class, post.getAuthor());

            Integer parentID = 0;
            if (post.getParent() != null && post.getParent() != 0) {
                try {
                    parentID = jdbcTemplate.queryForObject(PostQueries.CheckParentIdQuery, Integer.class, post.getParent(),
                            threadID);
                } catch (IncorrectResultSizeDataAccessException exeption) {
                    throw new NoSuchElementException("Parent not found!");
                }
            }

            post.setParent(parentID);

            final Array array = (parentID == 0) ? null : jdbcTemplate.queryForObject(PostQueries.GetPathByIdQuery, Array.class, parentID);

            final Long root = (parentID == 0) ? postID : ((Long[]) array.getArray())[0];
            jdbcTemplate.update(PostQueries.CreateSinglePostQuery, userID, userID, currentTime, forumID, forumID, postID, post.getMessage(),
                    parentID, threadID, array, postID, root);

            jdbcTemplate.update(ForumVisitorsQueries.AddNewVisitorQuery, userID, forumID);

        }

        jdbcTemplate.update(PostQueries.UpdatePostsCountQuery, newPosts.size(), forumID);

        return newPosts;
    }

    @Override
    public PostFull getFullPostById(Integer id, List<String> related) {
        final Post post = jdbcTemplate.queryForObject(PostQueries.GetPostByIdQuery, postRowMapper, id);
        final PostFull requestedPostFull = new PostFull(null, null, null, null);
        requestedPostFull.setPost(post);

        if (related != null) {
            for (String element : related) {
                switch (element) {
                    case "user":
                        User author = jdbcTemplate.queryForObject(UserQueries.GetUserByNicknameQuery, userRowMapper, post.getAuthor());
                        requestedPostFull.setAuthor(author);
                        break;
                    case "forum":
                        Forum forum = jdbcTemplate.queryForObject(ForumQueries.GetForumBySlugQuery, forumRowMapper, post.getForum());
                        requestedPostFull.setForum(forum);
                        break;
                    case "thread":
                        Thread thread = jdbcTemplate.queryForObject(ThreadQueries.GetThreadByIdQuery, threadRowMapper, post.getThread());
                        requestedPostFull.setThread(thread);
                        break;
                    default:
                        break;
                }
            }
        }

        return requestedPostFull;
    }

    @Override
    public Post updatePost(Integer id, PostUpdate postUpdate) {
        final Post updatedPost =
                jdbcTemplate.queryForObject(PostQueries.GetPostByIdQuery, postRowMapper, id);
        if (postUpdate.getMessage() == null || postUpdate.getMessage().equals(updatedPost.getMessage())) {
            return updatedPost;
        }
        jdbcTemplate.update(PostQueries.UpdatePostQuery, postUpdate.getMessage(), id);
        updatedPost.setIsEdited(Boolean.TRUE);
        updatedPost.setMessage(postUpdate.getMessage());
        return updatedPost;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(PostQueries.DeleteAllPostsQuery);
    }

    @Override
    public Integer amount() {
        return jdbcTemplate.queryForObject(PostQueries.GetStatusQuery, Integer.class);
    }
}
