package ru.kopylova.springcourse.DigitalLibrary.mail;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/*
Здесь используется spring-cloud-gateway (CGLIB). С SpringBoot2 идёт по умолчания, вместо динамического прокси JDK.
CGLIB применяют, когда объект не реализует интерфейсы.
Динамическое прокси предпочтительнее.
 */
@EnableAspectJAutoProxy
@Aspect
@Component
public class LogAspect {
    @Before("execution(* ru.kopylova.springcourse.DigitalLibrary.mail.service.EmailService.*(..))")
    public void before(JoinPoint jp) {
        System.out.println("jp.getSignature().getName() = " + jp.getSignature().getName());
    }
}
