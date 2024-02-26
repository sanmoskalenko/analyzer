package analyzer.code.repository;

import analyzer.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    private static final int H1_PARAM_IDX = 2;
    private static final int DESC_PARAM_IDX = 3;
    private static final int URL_ID_PARAM_IDX = 4;
    private static final int STATUS_CODE_PARAM_IDX = 5;
    private static final int CREATED_AT_PARAM_IDX = 6;

    public static void save(UrlCheck urlCheck) {
        String sql = "INSERT INTO url_checks (title, h1, description, url_id, status_code, created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, urlCheck.getTitle());
            stmt.setString(H1_PARAM_IDX, urlCheck.getH1());
            stmt.setString(DESC_PARAM_IDX, urlCheck.getDescription());
            stmt.setLong(URL_ID_PARAM_IDX, urlCheck.getUrlId());
            stmt.setLong(STATUS_CODE_PARAM_IDX, urlCheck.getStatusCode());
            stmt.setTimestamp(CREATED_AT_PARAM_IDX, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<UrlCheck> findById(Long urlId) {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            List<UrlCheck> checks = new ArrayList<>();

            while (resultSet.next()) {
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var createdAt = resultSet.getTimestamp("created_at");
                var urlCheck = new UrlCheck(id, statusCode, title, h1, description, urlId, createdAt);
                checks.add(urlCheck);
            }

            return checks;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
