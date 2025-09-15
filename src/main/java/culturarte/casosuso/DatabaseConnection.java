package culturarte.casosuso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - helper to provide a JDBC Connection when legacy code expects it.
 * The JDBC URL is built at runtime from environment variables so it's not hardcoded:
 * DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD, JDBC_PARAMS.
 *
 * Prefer using JPA (JPAUtil.getEntityManager()) for new code. This class exists to
 * maintain compatibility with panels that still use JDBC.
 */
public class DatabaseConnection {

    private static final String HOST = getenvOrDefault("DB_HOST", "localhost").trim();
    private static final String PORT = getenvOrDefault("DB_PORT", "33320").trim();
    private static final String DB_NAME = getenvOrDefault("DB_NAME", "culturarte").trim();
    private static final String USER = getenvOrDefault("DB_USER", "tecnologo");
    private static final String PASSWORD = getenvOrDefault("DB_PASSWORD", "tecnologo");
    private static final String JDBC_PARAMS = getenvOrDefault("JDBC_PARAMS", "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Driver missing: will fail later when attempting to getConnection.
            System.err.println("MySQL JDBC Driver not found on classpath: " + e.getMessage());
        }
    }

    private static String getenvOrDefault(String name, String def) {
        String v = System.getenv(name);
        return (v == null || v.isEmpty()) ? def : v;
    }

    private static String buildJdbcUrl() {
        String hostClean = HOST.replaceAll("\\s+", "");
        String portClean = PORT.replaceAll("\\s+", "");
        String base = String.format("jdbc:mysql://%s:%s/%s", hostClean, portClean, DB_NAME);
        if (JDBC_PARAMS != null && !JDBC_PARAMS.trim().isEmpty()) {
            String p = JDBC_PARAMS.trim();
            if (p.startsWith("?")) p = p.substring(1);
            return base + "?" + p;
        } else {
            return base;
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = buildJdbcUrl();
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
