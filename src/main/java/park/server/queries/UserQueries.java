package park.server.queries;

public class UserQueries {
    public static String CreateUserQuery = "INSERT INTO users (about, email, fullname, nickname) VALUES (?, ?, ?, ?)";

    public static String GetUserByNicknameQuery = "SELECT * FROM users WHERE LOWER(nickname) = LOWER(?)";

    public static String GetUserByNicknameOrEmailQuery = "SELECT * FROM users " +
            "WHERE (LOWER(nickname) = LOWER(?) OR LOWER(email) = LOWER(?))";

    public static String GetUserIdByNicknameQuery = "SELECT id FROM users WHERE LOWER(nickname) = LOWER(?)";

    public static String UpdateUserQuery = "UPDATE users SET about = ?, email = ?, fullname = ? WHERE LOWER(nickname) = LOWER(?)";

    public static String GetUserStatusQuery = "SELECT COUNT(*) FROM users";

    public static String DeleteAllUsersQuery = "TRUNCATE users CASCADE";
}
