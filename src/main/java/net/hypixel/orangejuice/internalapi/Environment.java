package net.hypixel.orangejuice.internalapi;

public enum Environment {
    PRODUCTION,
    DEV;

    public static boolean isProduction() {
        return getEnvironment().equals(PRODUCTION);
    }

    public static boolean isDev() {
        return getEnvironment().equals(DEV);
    }

    public static Environment getEnvironment() {
        if (System.getProperty("api.environment") == null) {
            return DEV;
        }

        return Environment.valueOf(System.getProperty("api.environment"));
    }
}
