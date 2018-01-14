package park.server.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import park.server.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PostRowMapper implements RowMapper<Post> {
    @Nullable
    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        final Timestamp timestamp = resultSet.getTimestamp("created");
        return new Post(resultSet.getString("author"), dateFormat.format(timestamp.getTime()),
                resultSet.getString("forum"), resultSet.getInt("id"),
                resultSet.getBoolean("is_edited"), resultSet.getString("message"),
                resultSet.getInt("parent_id"), resultSet.getInt("thread_id"));
    }
}
