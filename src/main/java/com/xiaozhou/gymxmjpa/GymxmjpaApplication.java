package com.xiaozhou.gymxmjpa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 健身房管理系统1.0启动类
 * @Author: LiuJian
 * @Date: 2020/4/1
 */
@MapperScan("com.xiaozhou.gymxmjpa.mapper")
@SpringBootApplication
public class GymxmjpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymxmjpaApplication.class, args);
    }
}
