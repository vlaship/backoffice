package dev.vlaship.backoffice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final String LOG = "log";

    @Before("execution(public * dev.vlaship.backoffice.facade.*.*(..)) || execution(public * dev.vlaship.backoffice.service.*.*(..))")
    public void logging(final JoinPoint pjp) throws Throwable {
        var log = getLogger(pjp);
        if (log != null) {
            var method = ((MethodSignature) pjp.getSignature()).getMethod();
            log.info("{}{}", method.getName(), pjp.getArgs());
        }
    }

    @Around("execution(public * dev.vlaship.backoffice.controller.*.*(..))")
    public Object executionTime(final ProceedingJoinPoint pjp) throws Throwable {
        var sw = new StopWatch();

        sw.start();
        var result = pjp.proceed();
        sw.stop();

        var log = getLogger(pjp);
        if (log != null) {
            var method = ((MethodSignature) pjp.getSignature()).getMethod();
            log.info("{} execution time {}ms", method.getName(), sw.getTotalTimeMillis());
        }

        return result;
    }

    private Logger getLogger(JoinPoint pjp) throws Throwable {
        var logField = Arrays.stream(pjp.getTarget().getClass().getDeclaredFields())
                .filter(field -> LOG.equalsIgnoreCase(field.getName()))
                .findFirst();

        if (logField.isEmpty()) {
            return null;
        }

        var canAccess = logField.get().canAccess(null);
        logField.get().setAccessible(true);
        var log = (Logger) logField.get().get(null);
        logField.get().setAccessible(canAccess);
        return log;
    }
}
