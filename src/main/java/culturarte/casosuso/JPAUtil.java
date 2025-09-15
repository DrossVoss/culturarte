
package culturarte.casosuso;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * JPAUtil - Inicializa el EntityManagerFactory pasando propiedades JDBC en runtime.
 * Lee preferencias desde -D properties o variables de entorno:
 *   -DDB_HOST / DB_HOST
 *   -DDB_PORT / DB_PORT
 *   -DDB_NAME / DB_NAME
 *   -DDB_USER / DB_USER
 *   -DDB_PASSWORD / DB_PASSWORD
 *   -DJDBC_PARAMS / JDBC_PARAMS
 *
 * Por defecto usa puerto 33320 y hbm2ddl.auto=update (modo desarrollo).
 */
public class JPAUtil {
    private static EntityManagerFactory emf;

    public static synchronized void init() {
        if (emf != null && emf.isOpen()) return;

        String host = System.getProperty("DB_HOST", System.getenv().getOrDefault("DB_HOST", "127.0.0.1"));
        String port = System.getProperty("DB_PORT", System.getenv().getOrDefault("DB_PORT", "33320"));
        String db = System.getProperty("DB_NAME", System.getenv().getOrDefault("DB_NAME", "culturarte"));
        String user = System.getProperty("DB_USER", System.getenv().getOrDefault("DB_USER", "tecnologo"));
        String pass = System.getProperty("DB_PASSWORD", System.getenv().getOrDefault("DB_PASSWORD", "tecnologo"));
        String params = System.getProperty("JDBC_PARAMS", System.getenv().getOrDefault("JDBC_PARAMS", "useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"));

        String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?%s", host, port, db, params);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.connection.url", jdbcUrl);
        props.put("hibernate.connection.username", user);
        props.put("hibernate.connection.password", pass);
        props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

        // default to update for development (Option A)
        props.put("hibernate.hbm2ddl.auto", System.getProperty("HIBERNATE_HBM2DDL", "update"));

        // keep some sane defaults (can be overridden in persistence.xml or via props)
        props.putIfAbsent("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.putIfAbsent("hibernate.show_sql", "false");
        props.putIfAbsent("hibernate.format_sql", "false");
        props.putIfAbsent("hibernate.jdbc.lob.non_contextual_creation", "true");

        System.out.println("JPAUtil: connecting to " + jdbcUrl + " as " + user);
        // Probe: check TCP connectivity to DB host:port before initializing Hibernate
        try {
            int portNum = Integer.parseInt(port);
            java.net.Socket sock = new java.net.Socket();
            try {
                sock.connect(new java.net.InetSocketAddress(host, portNum), 2000);
                System.out.println("JPAUtil: TCP connection to " + host + ":" + port + " ok.");
            } finally {
                try { sock.close(); } catch (Throwable t) { /* ignore */ }
            }
        } catch (NumberFormatException nfe) {
            System.err.println("JPAUtil: DB_PORT no es numérico: " + port);
        } catch (Throwable connEx) {
            System.err.println("JPAUtil: No se pudo conectar TCP a " + host + ":" + port + " -> " + connEx.getMessage());
            System.err.println("JPAUtil: Verifica que el servidor MySQL esté escuchando y que el puerto esté mapeado (p.ej. en Docker).");
            // también intentar DriverManager.getConnection para capturar el error preciso
            try {
                java.sql.DriverManager.setLoginTimeout(2);
                java.sql.Connection testc = null;
                try { testc = java.sql.DriverManager.getConnection(jdbcUrl, user, pass); }
                finally { if (testc != null) try { testc.close(); } catch (Throwable t) {} }
            } catch (Throwable dmEx) {
                System.err.println("JPAUtil: DriverManager.getConnection falló: " + dmEx.getMessage());
                dmEx.printStackTrace(System.err);
            }
            throw new RuntimeException("No se pudo conectar TCP a " + host + ":" + port + ". Revisa la configuración del servidor MySQL y el mapeo de puertos.", connEx);
        }
        try {
            emf = Persistence.createEntityManagerFactory("culturartePU", props);
        } catch (Throwable ex) {
            System.err.println("JPAUtil: error creating EntityManagerFactory: " + ex.getMessage());
            ex.printStackTrace(System.err);
            // Diagnóstico adicional: verificar driver en classpath
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("JPAUtil: MySQL driver com.mysql.cj.jdbc.Driver está en classpath.");
            } catch (Throwable t) {
                System.err.println("JPAUtil: NO se encontró com.mysql.cj.jdbc.Driver en classpath: " + t.getMessage());
            }
            // Intentar con propiedades estándar javax.persistence como fallback
            try {
                Map<String, Object> props2 = new HashMap<>();
                props2.putAll(props);
                props2.put("javax.persistence.jdbc.url", jdbcUrl);
                props2.put("javax.persistence.jdbc.user", user);
                props2.put("javax.persistence.jdbc.password", pass);
                props2.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                System.out.println("JPAUtil: intentando nuevo createEntityManagerFactory con propiedades javax.persistence.*");
                emf = Persistence.createEntityManagerFactory("culturartePU", props2);
            } catch (Throwable ex2) {
                System.err.println("JPAUtil: fallback también falló: " + ex2.getMessage());
                ex2.printStackTrace(System.err);
                throw new RuntimeException("No se pudo inicializar JPA EntityManagerFactory. Revisa el stacktrace previo.", ex2);
            }
        }
    }

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) init();
        return emf;
    }


    public static synchronized javax.persistence.EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}
