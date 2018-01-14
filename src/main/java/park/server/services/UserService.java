package park.server.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import park.server.DAO.UserDAO;
import park.server.model.User;
import park.server.model.UserUpdate;
import park.server.queries.UserQueries;
import park.server.rowmappers.UserRowMapper;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService implements UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<User> userRowMapper = new UserRowMapper();

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(@NotNull String nickname, @NotNull User newUser) {
        jdbcTemplate.update(UserQueries.CreateUserQuery, newUser.getAbout(), newUser.getEmail(), newUser.getFullname(), nickname);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return jdbcTemplate.queryForObject(UserQueries.GetUserByNicknameQuery, userRowMapper, nickname);
    }

    @Override
    public List<User> getUserByNicknameOrEmail(String nickname, String email) {
        return jdbcTemplate.query(UserQueries.GetUserByNicknameOrEmailQuery, userRowMapper, nickname, email);
    }

    @Override
    public User updateUser(String nickname, UserUpdate user) {
        final User updatedUser = getUserByNickname(nickname);
        if (user.getFullname() != null) {
            updatedUser.setFullname(user.getFullname());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        if (user.getAbout() != null) {
            updatedUser.setAbout(user.getAbout());
        }
        jdbcTemplate.update(UserQueries.UpdateUserQuery, updatedUser.getAbout(), updatedUser.getEmail(), updatedUser.getFullname(), nickname);

        return getUserByNickname(nickname);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(UserQueries.DeleteAllUsersQuery);
    }

    @Override
    public Integer amount() {
        return jdbcTemplate.queryForObject(UserQueries.GetUserStatusQuery, Integer.class);
    }
}
