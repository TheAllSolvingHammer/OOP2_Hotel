package com.tuvarna.hotel.domain.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;



public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Around("@annotation(com.tuvarna.hotel.domain.aspect.LogExecution)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String inputArgs= Arrays.toString(joinPoint.getArgs());
        String className=joinPoint.getSignature().getDeclaringTypeName();
        System.out.printf("Starting execution \"{}\", in class \"{}\", with args \"{}\" ", methodName,className,inputArgs);
        Object result = joinPoint.proceed();
        logger.info("Finished execution method \"{}\", in class\"{}\", with result \"{}\" ", methodName,className,result);
        return result;
    }
}
