package com.umg.adquisiciones_v2.config;

public class DatabaseContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setDatabaseType(String dbType) {
        CONTEXT.set(dbType);
    }

    public static String getDatabaseType() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
