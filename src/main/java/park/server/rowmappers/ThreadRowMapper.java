package park.server.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import park.server.model.Thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ThreadRowMapper implements RowMapper<Thread> {
    @Nullable
    @Override
    public Thread mapRow(ResultSet resultSet, int i) throws SQLException {
        final Timestamp timestamp = resultSet.getTimestamp("created");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new Thread(resultSet.getString("author"), dateFormat.format(timestamp.getTime()),
                resultSet.getString("forum"), resultSet.getInt("id"),
                resultSet.getString("message"), resultSet.getString("slug"),
                resultSet.getString("title"), resultSet.getInt("votes"));
    }
}
