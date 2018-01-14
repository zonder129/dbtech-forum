package park.server.DAO;

import park.server.model.User;
import park.server.model.UserUpdate;

import java.util.List;

public interface UserDAO {
    void createUser(String nickname, User newUser);

    User getUserByNickname(String nickname);

    List<User> getUserByNicknameOrEmail(String nickname, String email);

    User updateUser(String nickname, UserUpdate user);

    void deleteAll();

    Integer amount();
}
