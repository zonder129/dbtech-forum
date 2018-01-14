package park.server.queries;

public class ForumVisitorsQueries {
    public static String AddNewVisitorQuery = "INSERT INTO forum_visitors (user_id, forum_id) VALUES (?, ?) "
            + "ON CONFLICT (user_id, forum_id) DO NOTHING";
    public static String DeleteAllForumVisitorsQuery = "TRUNCATE forum_visitors CASCADE";
}
