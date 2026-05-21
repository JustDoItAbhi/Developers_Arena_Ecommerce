package ecommerce_backend.utils;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

    @Around("@annotation(TrackPerformance)")
    public Object trackPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        long endTime = System.nanoTime();
        long responseTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        Long dbQueryTime = DbTimeHolder.getDbTime();

        PerformanceMetrics metrics = PerformanceMetrics.builder()
                .apiResponseTime(responseTime)
                .databaseQueryTime(dbQueryTime != null ? dbQueryTime : 0)
                .memoryUsage(getMemoryUsage())
                .build();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("PERFORMANCE METRICS START");
        System.out.println("=".repeat(60));
        metrics.print();
        System.out.println("=".repeat(60));
        System.out.println("PERFORMANCE METRICS END");
        System.out.println("=".repeat(60) + "\n");
        log.info("Performance Metrics - API: {}ms, DB: {}ms, Memory: {}",
                responseTime, dbQueryTime, getMemoryUsage());
        return result;
    }

    private String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        return usedMemory + "MB";
    }
}