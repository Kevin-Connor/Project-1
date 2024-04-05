package com.skillstorm.inventorymanagement.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;              
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skillstorm.inventorymanagement.models.Warehouse;


@Component
@Aspect         
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.skillstorm.inventorymanagement.controllers.ShirtController)")  
    public void checkShirt() {
        
    }

    @Pointcut("execution(public * saveWarehouse(com.skillstorm.inventorymanagement.models.Warehouse)) && args(warehouseToBeSaved)")
    public void checkWarehouse(Warehouse warehouseToBeSaved) {

    }

    @Before("checkShirt()")
    public void request(JoinPoint joinPoint) {
        log.debug("A request was made to {} with the argument(s): {}",
            joinPoint.getSignature(),
            Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning(value = "checkShirt()", returning = "returnedData")
    public void response(JoinPoint joinPoint, Object returnedData) {
        log.debug("A response was sent from {} with the returned data: {}",
            joinPoint.getSignature().getName(),
            returnedData.toString()
        );
    }

    @Around("checkWarehouse(warehouseToBeSaved)")
    public Warehouse logWarehouses(ProceedingJoinPoint proceedingJoinPoint, Warehouse warehouseToBeSaved) {
    
        log.debug("WAREHOUSE: {}", warehouseToBeSaved.toString());

        if(warehouseToBeSaved.getId() == 0) {
            try {
                proceedingJoinPoint.proceed();
            } catch(Throwable e) {
                log.error("Method could not be executed", e);
            }
            log.debug("WAREHOUSE WAS CREATED");
        }
        else {
            log.debug("WAREHOUSE WAS NOT CREATED - ALREADY EXISTS");
        }

        return warehouseToBeSaved;
    }
    
}
