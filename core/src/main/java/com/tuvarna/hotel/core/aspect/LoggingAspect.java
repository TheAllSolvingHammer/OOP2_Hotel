package com.tuvarna.hotel.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import java.util.Arrays;

@Slf4j
public class LoggingAspect {

    @Around("@annotation(com.tuvarna.hotel.core.aspect.LogExecution)")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String inputArgs= Arrays.toString(joinPoint.getArgs());
        String className=joinPoint.getSignature().getDeclaringTypeName();
        log.info("Starting execution \"{}\", in class \"{}\", with args \"{}\" ", methodName,className,inputArgs);
        Object result = joinPoint.proceed();
        log.info("Finished execution method \"{}\", in class\"{}\", with result \"{}\" ", methodName,className,result);
        return result;
    }
}
