package com.jy.im.server;

import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.common.constants.EnvConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ImServerStarter {

    private static final Logger logger = LoggerFactory.getLogger(ImServerStarter.class);

    private Environment env;

    @Autowired
    private DefaultLauncher defaultLauncher;

    public ImServerStarter(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImServerStarter.class);
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(EnvConstants.SPRING_PROFILE_DEFAULT, EnvConstants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
        Environment env = app.run(args).getEnvironment();
        logger.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running!\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getActiveProfiles());
    }

    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(EnvConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(EnvConstants.SPRING_PROFILE_PRODUCTION)) {
            logger.error("You have misConfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(EnvConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(EnvConstants.SPRING_PROFILE_CLOUD)) {
            logger.error("You have misConfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
        defaultLauncher.startup();
    }

}
