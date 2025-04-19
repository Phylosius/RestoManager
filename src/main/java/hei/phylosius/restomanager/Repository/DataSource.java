package hei.phylosius.restomanager.Repository;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Repository
public class DataSource {
    private final Connection connection;
    private final String username;
    private final String url;
    private final String password;

    @Autowired
    public DataSource(@Value("${datasource.username}") String username,
                      @Value("${datasource.password}") String password,
                      @Value("${datasource.url}") String url) {
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
