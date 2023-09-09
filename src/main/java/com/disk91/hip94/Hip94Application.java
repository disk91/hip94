package com.disk91.hip94;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@Configuration
@SpringBootApplication
public class Hip94Application  implements CommandLineRunner, ExitCodeGenerator {

    public static ApplicationContext context;
    public static boolean requestingExitForStartupFailure = false;

    public static void main(String[] args) {
        context = SpringApplication.run(Hip94Application.class, args);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        long pid = ProcessHandle.current().pid();
        System.out.println("-------------- GO ("+pid+")--------------");
    }

    public static void exit() {
        System.out.println("---------- SHUTDOWN WAIT------------");
        int exitCode = SpringApplication.exit(context,new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return 0;
            }
        });
        System.out.println("------------- GONE --------------");
    }

    public int getExitCode() {
        return 0;
    }

}
