package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Push   // 서버에서 클라이언트로 메시지를 푸시하는 기능을 활성화. 웹소켓 연결을 사용.
/*
 * 서버 푸시를 활성화한 후에는 다양한 방법으로 트리거할 수 있음.
 * 이 중 가장 쉬운 방법은 모든 스레드에서 호출할 수 있는 UI.access()
 */
public class Application implements AppShellConfigurator {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
