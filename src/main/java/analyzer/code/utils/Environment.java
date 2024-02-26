package analyzer.code.utils;

public class Environment {

    private static boolean isTestRun;
    private static String testJdbcUrl;
    private static String testJdbcDriver;

    private static final String DEFAULT_PORT = "7070";

    public static final String H2_JDBC_URL = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;LOCK_MODE=0";
    public static final String SCHEMA_FILE_NAME = "schema.sql";
    public static final String TEMPLATES_FOLDER_NAME = "templates";
    public static final Integer APP_PORT = Integer.parseInt(System.getenv().getOrDefault("APP_PORT", DEFAULT_PORT));

    public static String getJdbcUrl() {
        return isTestRun ? testJdbcUrl : System.getenv().getOrDefault("JDBC_DATABASE_URL", H2_JDBC_URL);
    }

    public static String getJdbcDriver() {
        if (isTestRun) {
            return testJdbcDriver;
        }

        return getJdbcUrl().contains("postgres") ? "org.postgresql.Driver" : "org.h2.Driver";
    }

    public static void initTestProfile(String jdbcUrl) {
        isTestRun = true;
        testJdbcDriver = "org.h2.Driver";
        testJdbcUrl = jdbcUrl;
    }

}
