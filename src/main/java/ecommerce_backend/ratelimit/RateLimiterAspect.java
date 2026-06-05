package ecommerce_backend.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimiterAspect {
    private final Map<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();

    public synchronized boolean allowRequest(String key, int maxRequests, long windowMs) {
        long now = System.currentTimeMillis();

        requestMap.putIfAbsent(key, new ArrayDeque<>());
        Deque<Long> timestamps = requestMap.get(key);

        while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) > windowMs) {
            timestamps.pollFirst();
        }

        if (timestamps.size() >= maxRequests) {
            return false;
        }

        timestamps.addLast(now);
        return true;
    }
}