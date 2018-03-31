package com.jy.im.common.constants;

public final class EnvConstants {

    public static final String SPRING_PROFILE_LOCALMENT = "local";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";

    public static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private EnvConstants() {
    }
}
