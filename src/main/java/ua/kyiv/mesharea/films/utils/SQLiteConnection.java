package ua.kyiv.mesharea.films.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.jdbc4.JDBC4Connection");
            // путь к БД желательно выносить в отдельный файл настроек
            String url = "jdbc:sqlite:D:\\dev\\source\\projects\\wfilms\\src\\main\\resources\\db\\films.db";
            return DriverManager.getConnection(url);

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
