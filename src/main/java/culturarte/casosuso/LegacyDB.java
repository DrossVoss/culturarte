package culturarte.casosuso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * LegacyDB - helper to read legacy schema directly via JDBC when JPA mapping doesn't match DB.
 * Provides simple methods that return List<String> items in the "id:display" format used by panels.
 */
public class LegacyDB {

    public static List<String> listUsuariosByTipo(String tipoHuman) throws Exception {
        List<String> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql;
            if (tipoHuman == null) {
                sql = "SELECT id, apodo FROM usuarios ORDER BY apodo";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        out.add(rs.getString("id") + ":" + rs.getString("apodo"));
                    }
                }
            } else {
                String t = tipoHuman;
                if (t.length() > 1) {
                    String up = t.toUpperCase();
                    if (up.startsWith("COL")) t = "C";
                    else if (up.startsWith("PROP")) t = "P";
                    else t = t.substring(0,1).toUpperCase();
                }
                sql = "SELECT id, apodo FROM usuarios WHERE tipo = ? ORDER BY apodo";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, t);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            out.add(rs.getString("id") + ":" + rs.getString("apodo"));
                        }
                    }
                }
            }
        }
        return out;
    }

    public static List<String> listPropuestas() throws Exception {
        List<String> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT ref, titulo FROM propuestas ORDER BY titulo";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(rs.getString("ref") + ":" + rs.getString("titulo"));
                }
            }
        }
        return out;
    }

    public static List<String> listCategorias() throws Exception {
        List<String> out = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT ref, nombre FROM categorias ORDER BY nombre";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(rs.getString("ref") + ":" + rs.getString("nombre"));
                }
            }
        }
        return out;
    }
}
