package park.server.queries;

public class ForumQueries {
    public static String CreateForumByUserQuery = "INSERT INTO forums (slug, title, user_id, user_nickname) " +
        "VALUES (?, ?, (SELECT id FROM users WHERE LOWER(nickname) = LOWER(?))," +
        "(SELECT nickname FROM users WHERE LOWER(nickname) = LOWER(?)))";

    public static String GetForumBySlugQuery = "SELECT f.posts, f.slug, f.threads, " +
            "f.title, user_nickname AS user FROM forums f WHERE LOWER(f.slug) = LOWER(?)";

    public static String GetStatusQuery = "SELECT COUNT(*) FROM forums";

    public static String DeleteAllForumQuery = "TRUNCATE forums CASCADE";

    public static String GetThreadsQuery(final Integer limit, final String since, final Boolean desc) {
        StringBuilder builder = new StringBuilder("SELECT author, created, forum, ");
        builder.append("id, message, slug, title, votes ");
        builder.append("FROM threads WHERE LOWER(forum) = LOWER(?) ");

        String sign = (desc == Boolean.TRUE ? "<= " : ">= ");

        if (since != null) {
            builder.append("AND created ").append(sign).append("?::TIMESTAMPTZ ");
        }

        builder.append("ORDER BY created ");

        if (desc == Boolean.TRUE) {
            builder.append("DESC ");
        }

        if (limit != null) {
            builder.append("LIMIT ? ");
        }

        return builder.toString();
    }

    public static String GetUsersQuery(final Integer limit, final String since, final Boolean desc) {
        String compare = (desc != null && desc ? "< " : "> ");
        String order = (desc != null && desc ? "DESC " : "ASC ");

        StringBuilder builder = new StringBuilder("SELECT about, email, fullname, nickname ");
        builder.append("FROM users u JOIN forum_visitors f ON (u.id = f.user_id) ");
        builder.append(" WHERE f.forum_id = ? ");
        if (since != null) {
            builder.append(" AND nickname COLLATE \"ucs_basic\" ").append(compare).append("'").append(String.valueOf(since)).append("' COLLATE \"ucs_basic\" ");
        }
        builder.append(" ORDER BY nickname COLLATE \"ucs_basic\" ").append(order);
        if (limit != null) {
            builder.append("LIMIT ").append(String.valueOf(limit));
        }

        return builder.toString();
    }

    public static String GetForumSlugByIdQuery = "SELECT slug FROM forums WHERE id = ?";

    public static String GetForumIdBySlugQuery = "SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)";
}
