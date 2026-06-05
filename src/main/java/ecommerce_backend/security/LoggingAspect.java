package ecommerce_backend.security;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Before("execution( ecommerce_backend.service.*.(...))")
    public void logBefore(JoinPoint joinPoint){
        logger.info("Entering method: {}",joinPoint.getSignature().getName());
    }
}
