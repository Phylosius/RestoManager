package dao;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class DataSource {
    private final Connection connection;
    private final String username;
    private final String url;
    private final String password;

    public DataSource(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;

        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
