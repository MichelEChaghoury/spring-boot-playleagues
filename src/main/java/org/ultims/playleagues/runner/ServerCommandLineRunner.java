package org.ultims.playleagues.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {

    private final Environment env;
    private final Logger logger;

    @Autowired
    public ServerCommandLineRunner(Environment env) {
        this.env = env;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void run(String... args) {
        logger.info("Server running on http://localhost:" + env.getProperty("server.port"));
    }
}
