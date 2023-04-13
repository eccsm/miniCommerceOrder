package tr.nttdata.poc.minicommerce.order.aop;

import lombok.extern.slf4j.Slf4j;
import tr.nttdata.poc.minicommerce.order.model.Order;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Before("@annotation(tr.nttdata.poc.minicommerce.order.annotation.LogObjectBefore)")
    public void logSportsIconBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Order) {
                Order order = (Order) arg;
                log.info("******* Order before :: {}", order);

            }
        }
    }

    @AfterReturning(value = "@annotation(tr.nttdata.poc.minicommerce.order.annotation.LogObjectAfter)", returning = "result")
    public void logSportsIconAfter(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(result)) {
            if (result instanceof ResponseEntity) {
                ResponseEntity responseEntity = (ResponseEntity) result;

                if (responseEntity.getStatusCode().value() == 200)
                    log.info("******* Returning object :: {}", responseEntity.getBody());
                else
                    log.error("Something went wrong while logging...!");
            }
        }
    }
}
