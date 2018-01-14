package park.server.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import park.server.model.Forum;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForumRowMapper implements RowMapper<Forum> {

    @Nullable
    @Override
    public Forum mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Forum(resultSet.getLong("posts"), resultSet.getString("slug"),
                resultSet.getInt("threads"), resultSet.getString("title"),
                resultSet.getString("user"));
    }
}
