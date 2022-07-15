package com.ycicic.fivehearts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ycicic
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FiveHeartsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FiveHeartsApplication.class, args);
    }
}
