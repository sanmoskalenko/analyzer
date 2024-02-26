package analyzer.code.utils;

import com.zaxxer.hikari.HikariDataSource;
import analyzer.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class TestUtils {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final String HTML_SAMPLE = "index.html";
    public static final String SQL_URLS_SAMPLE = "urls.sql";
    public static final String SQL_URL_CHECKS_SAMPLE = "url-checks.sql";

    public static String getFixture(String fileName) throws IOException {
        var inputStream = TestUtils.class.getClassLoader().getResourceAsStream("fixtures/" + fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(""));
        }
    }

    private static String readSchemaFile() throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(Environment.SCHEMA_FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(""));
        }
    }

    private static void executeSql(HikariDataSource dataSource, String sql) throws SQLException {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public static void loadSchema(HikariDataSource dataSource) throws IOException, SQLException {
        executeSql(dataSource, readSchemaFile());
    }

    public static void loadSampleData(HikariDataSource dataSource, String sqlSample) throws IOException, SQLException {
        executeSql(dataSource, getFixture(sqlSample));
    }

}
