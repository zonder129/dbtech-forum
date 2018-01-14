package park.server.queries;

public class PostQueries {
    @Deprecated
    public static String CreatePostQuery(final Integer numberOfPosts) {

        StringBuilder builder = new StringBuilder(
                "INSERT INTO posts (user_id, created, forum_id, id, message, parent_id, thread_id, path, root_id) VALUES "
        );

        for (Integer i = 0; i < numberOfPosts; ++i) {
            builder.append("( ?, ?::TIMESTAMPTZ, ?, ?, ?, ?, ?, array_append(?, ?::BIGINT), ?)");
            if (i != numberOfPosts - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    public static String CreateSinglePostQuery = "INSERT INTO posts " +
            "(user_id, author, created, forum_id, forum, id, message, parent_id, thread_id, path, root_id) " +
            "VALUES (?, (SELECT nickname FROM users WHERE id = ?), ?::TIMESTAMPTZ, ?," +
            "(SELECT slug FROM forums WHERE id = ?), ?, ?, ?, ?, array_append(?, ?::BIGINT), ?)";

    public static String UpdatePostQuery = "UPDATE posts SET message = ?, is_edited = TRUE WHERE id = ?";

    public static String GetPostByIdQuery =
            "SELECT author, created, forum, id, is_edited, message, parent_id, thread_id " +
                    "FROM posts WHERE id = ?";

    public static String GetStatusQuery = "SELECT COUNT(*) FROM posts";

    public static String DeleteAllPostsQuery = "TRUNCATE posts CASCADE";

    public static String UpdatePostsCountQuery =
            "UPDATE forums " +
                    "SET posts = posts + ? " +
                    "WHERE id = ?";

    public static String CheckParentIdQuery = "SELECT id FROM posts WHERE id = ? AND thread_id = ?";

    public static String GetPathByIdQuery = "SELECT path FROM posts WHERE id = ?";

    public static String GetNextIdQuery = "SELECT nextval(pg_get_serial_sequence('posts', 'id'))";
}
