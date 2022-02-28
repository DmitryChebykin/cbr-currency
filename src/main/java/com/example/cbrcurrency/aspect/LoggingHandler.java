package com.example.cbrcurrency.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingHandler {

    @Pointcut("execution(public * com.example.cbrcurrency.service..*(..))")
    public void allMethod() {
    }

    @AfterReturning(pointcut = "allMethod()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {

        log.info("{}|{}|{}|{}",
                "Entering in Method : " + joinPoint.getSignature().getName(),
                "Class Name :  " + joinPoint.getSignature().getDeclaringTypeName(),
                "Arguments :  " + Arrays.toString(joinPoint.getArgs()),
                "Target class : " + joinPoint.getTarget().getClass().getName()
        );

        log.info("Method Return value : " + this.getValue(result));
    }

    @AfterThrowing(pointcut = "allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

        log.error("{}|{}",
                "An exception has been thrown in : " + joinPoint.getSignature().getName() + "()",
                "Message : " + exception.getLocalizedMessage() + "()");
        if (exception.getCause() != null) {
            log.error("Cause : " + exception.getCause());
        }
    }

    @Around("allMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();

            long elapsedTime = System.currentTimeMillis() - start;

            log.info("Method " + className + "." + methodName + " ()" + " execution time : " + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }

    private String getValue(Object result) {

        String returnValue = null;

        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}