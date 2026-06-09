package ecommerce_backend.utils;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);
    private static final AtomicLong totalRequests = new AtomicLong();
    private static final AtomicLong failedRequests = new AtomicLong();
    private static final AtomicLong activeRequests = new AtomicLong();
    @Around("@annotation(TrackPerformance)")
    public Object trackPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        activeRequests.incrementAndGet();
        totalRequests.incrementAndGet();
        long startTime = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            failedRequests.incrementAndGet();
            activeRequests.incrementAndGet();
            throw e;
        } finally {
            activeRequests.decrementAndGet();
            long endTime = System.nanoTime();
            long responseTime =TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            String apiName = joinPoint.getSignature().getName();
            Long dbQueryTime = DbTimeHolder.getDbTime();
            long numberofUsers=activeRequests.get();
            double errorPercentage =
                    totalRequests.get() == 0 ? 0 :
                            ((double) failedRequests.get() / totalRequests.get()) * 100;
            PerformanceMetrics metrics = PerformanceMetrics.builder()
                    .apiName(apiName)
                    .apiResponseTime(responseTime)
                    .databaseQueryTime(dbQueryTime != null ? dbQueryTime : 0)
                    .memoryUsage(getMemoryUsage())
                    .errorTime(errorPercentage)
                    .numberOfActiveUsers(numberofUsers)
                    .build();

            System.out.println("\n" + "=".repeat(60));
            System.out.println("API: " + apiName);
            System.out.println("Error Percentage: "
                    + String.format("%.2f", errorPercentage) + "%");
            metrics.print();
            System.out.println("=".repeat(60));

            log.info(
                    "API: {}, Response: {}ms, DB: {}ms, Error Rate: {}%",
                    apiName,
                    responseTime,
                    dbQueryTime,
                    String.format("%.2f", errorPercentage)
            );
        }
    }

    private String getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        return usedMemory + "MB";
    }
}