package com.fdu.capstone_instrument_shopping_center.DB_Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootTest
@Slf4j
public class DatabaseConnectionTest {
    // Test Mysql DB connection

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDBConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Successfully connected to mysql Database.");
            assert (connection != null);
        }catch (AssertionError e){
            log.error("DB Connection Assert error: " + e.getMessage());
        }
    }

    @Test
    public void checkEnvironmentVariable() {
        System.out.println("JWT_SECRET_KEY: " + System.getenv("JWT_SECRET_KEY"));
    }
}
