package park.server.queries;

import javax.validation.constraints.NotNull;

public class ThreadQueries {

    public static String CreateNewThreadQuery = "INSERT INTO threads (user_id, author, created, forum_id, forum, message, slug, title) "
            + "VALUES ((SELECT id FROM users WHERE nickname = ?), ?,"
            + " COALESCE(?::TIMESTAMPTZ, CURRENT_TIMESTAMP), "
            + "(SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)), "
            + "(SELECT slug FROM forums WHERE LOWER(slug) = LOWER(?)), ?, ?, ?) RETURNING id";


    public static String GetThreadByIdQuery = "SELECT t.author AS author, t.created , forum AS forum, t.id, " +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t WHERE t.id = ?";

    public static String GetThreadBySlugQuery = "SELECT t.author AS author, t.created , forum AS forum, t.id," +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t WHERE LOWER(t.slug) = LOWER(?)";

    public static String UpdateThreadQuery = "UPDATE threads SET title = ?, message = ? WHERE id = ?";

    public static String GetThreadsStatusQuery = "SELECT COUNT(*) FROM threads";

    public static String DeleteAllThreadsQuery = "TRUNCATE threads CASCADE";

    public static String GetForumIdBySlugOrIdQuery(@NotNull String slugOrId) {
        if (slugOrId.matches("\\d+")) {
            return "SELECT f.id FROM threads t JOIN forums f ON (f.id = t.forum_id) WHERE t.id = ?::INTEGER";
        }
        else {
            return "SELECT f.id FROM threads t JOIN forums f ON (f.id = t.forum_id) WHERE LOWER(t.slug) = LOWER(?)";
        }
    }

    public static String GetThreadIdBySlugQuery = "SELECT id FROM threads WHERE LOWER(slug) = LOWER(?)";

    public static String UpdateThreadsCountQuery = "UPDATE forums SET threads = threads + ? WHERE LOWER(slug) = LOWER(?)";

    public static String AddVoteQuery = "INSERT INTO votes (user_id, thread_id, voice) VALUES (?, ?, ?)";

    public static String UpdateThreadVotesQuery = "UPDATE threads SET votes = votes + ? WHERE id = ?";

    public static String CheckPreviousVoteQuery = "SELECT voice FROM votes WHERE user_id = ? AND thread_id = ?";

    public static String UpdateVoteQuery = "UPDATE votes SET voice = ? WHERE user_id = ? AND thread_id = ?";

    public static String CheckThreadExistenceQuery = "SELECT id FROM threads WHERE id = ?";

    public static String GetThreadPostsInFlatOrder(@NotNull Integer limit, @NotNull Integer since, @NotNull Boolean desc) {

        StringBuilder resultQuery = new StringBuilder("SELECT p.author AS author, p.created, p.forum AS forum, p.id, p.is_edited," +
                " p.message, p.parent_id, p.thread_id FROM posts p WHERE p.thread_id = ? ");

        String order = (desc != null && desc ? " DESC " : " ASC ");
        String sign = (desc != null && desc ? " < " : " > ");

        if (since != null) {
            resultQuery.append(" AND p.id").append(sign).append("? ");
        }
        resultQuery.append("ORDER BY p.id ").append(order);

        if (limit != null) {
            resultQuery.append("LIMIT ?");
        }

        return resultQuery.toString();
    }

    public static String GetThreadPostsInTreeOrder(@NotNull Integer limit, @NotNull Integer since, @NotNull Boolean desc ) {

        StringBuilder resultQuery = new StringBuilder("SELECT p.author AS author, p.created, p.forum AS forum, p.id, p.is_edited,"
            +" p.message, p.parent_id, p.thread_id FROM posts p WHERE p.thread_id = ? ");

        String order = (desc != null && desc ? " DESC " : " ASC ");
        String sign = (desc != null && desc ? " < " : " > ");

        if (since != null) {
            resultQuery.append(" AND p.path ").append(sign).append("(SELECT path FROM posts WHERE id = ?) ");
        }
        resultQuery.append("ORDER BY p.path ").append(order);

        if (limit != null) {
            resultQuery.append("LIMIT ?");
        }

        return resultQuery.toString();
    }

    public static String GetThreadPostsInParentTreeOrder(final Integer limit, final Integer since, final Boolean desc ) {

        String order = (desc != null && desc ? " DESC " : " ASC ");
        String sign = (desc != null && desc ? " < " : " > ");

        StringBuilder resultQuery = new StringBuilder("SELECT p.author AS author, p.created, p.forum AS forum, p.id, p.is_edited,"
                + " p.message, p.parent_id, p.thread_id FROM posts p"
                + " WHERE p.root_id IN (SELECT id FROM posts WHERE thread_id = ? AND parent_id=0 ");

        if (since != null) {
            resultQuery.append(" AND path ").append(sign).append("(SELECT path FROM posts WHERE id = ?) ");
        }
        resultQuery.append("ORDER BY id ").append(order);

        if (limit != null) {
            resultQuery.append(" LIMIT ?");
        }
        resultQuery.append(") ");


        resultQuery.append("ORDER BY p.path ").append(order);


        return resultQuery.toString();
    }
}
