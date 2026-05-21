package ecommerce_backend.utils;

import java.util.concurrent.TimeUnit;



public class DbTimeHolder {
    private static final ThreadLocal<Long> dbStartTimeHolder = new ThreadLocal<>();
    private static final ThreadLocal<Long> dbTimeHolder = new ThreadLocal<>();

    public static void setDbStartTime(long startTime) {
        dbStartTimeHolder.set(startTime);
    }

    public static void recordDbTime() {
        Long startTime = dbStartTimeHolder.get();
        if (startTime != null) {
            long dbTime = System.nanoTime() - startTime;
            dbTimeHolder.set(dbTime);
            clear();
        }
    }

    public static Long getDbTime() {
        Long dbTime = dbTimeHolder.get();
        if (dbTime != null) {
            long dbTimeInMillis = TimeUnit.NANOSECONDS.toMillis(dbTime);
            dbTimeHolder.remove();
            return dbTimeInMillis;
        }
        return null;
    }

    public static void clear() {
        dbStartTimeHolder.remove();
    }

    public static void clearAll() {
        dbStartTimeHolder.remove();
        dbTimeHolder.remove();
    }
}