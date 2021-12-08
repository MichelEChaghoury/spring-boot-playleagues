package org.ultims.playleagues.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseApplicationRunner implements ApplicationRunner {

    private final Logger logger;

    @Autowired
    public DatabaseApplicationRunner() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            String userName = args.getOptionValues("user.name").get(0);
            logger.info("user.name: {}", userName);

            String password = args.getOptionValues("user.password").get(0);
            logger.info("user.password: {}", password);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }
}