package online.gettrained.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationModule {

  public static void main(String[] args) {
    SpringApplication.run(SharedModule.class, args);
  }
}
