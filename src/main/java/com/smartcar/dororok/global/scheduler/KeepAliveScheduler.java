package com.smartcar.dororok.global.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class KeepAliveScheduler {

    @Autowired
    private DataSource dataSource;

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void keepAlive() {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("SELECT 1").execute();
        } catch (SQLException e) {
            // 예외 처리
            e.printStackTrace();
        }
    }
}
