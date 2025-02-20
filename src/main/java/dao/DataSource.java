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
    private final String host;
    private final String password;

    public DataSource(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;

        String url = "jdbc:postgres://" + host;
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
