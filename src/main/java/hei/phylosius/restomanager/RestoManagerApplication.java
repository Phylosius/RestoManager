package hei.phylosius.restomanager;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestoManagerApplication {

    public static void main(String[] args) {
        loadEnvVariables();

        SpringApplication.run(RestoManagerApplication.class, args);
    }

    public static void loadEnvVariables() {
        Dotenv dotenv = Dotenv.load();
        System.out.println(dotenv.get("DB_URL"));
        System.setProperty("datasource.url", dotenv.get("DB_URL"));
        System.setProperty("datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("datasource.password", dotenv.get("DB_PASSWORD"));
    }

}
