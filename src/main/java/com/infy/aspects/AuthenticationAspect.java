package com.infy.aspects;

import com.infy.constants.EmployeeType;
import com.infy.dto.EmployeeDto;
import com.infy.entities.Employee;
import com.infy.utils.SessionFields;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Stream;

@Aspect
@Component
public class AuthenticationAspect {

    private WebSession session;

    // Verify that the User is logged in:
    @Around("loggedIn()")
    public Object verifyLoggedIn(ProceedingJoinPoint pjp) throws Throwable {
        session = getSession(pjp);
        if(session == null || session.getAttribute(SessionFields.LOGGED_USER) == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        return pjp.proceed();
    }

    // Verify that User is logged in and that their username matches the username in the path:
    @Around("currentUser()")
    public Object verifyCurrentUser(ProceedingJoinPoint pjp) throws Throwable {
        session = getSession(pjp);
        if(session == null || session.getAttribute(SessionFields.LOGGED_USER) == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        EmployeeDto loggedUser = session.getAttribute(SessionFields.LOGGED_USER);
        String username = (String) Arrays.stream(pjp.getArgs())
                .filter(arg -> arg instanceof String)
                .findFirst()
                .orElse(null);
        if(username == null || !username.equals(loggedUser.getUsername())) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
        return pjp.proceed();
    }

    // Verify that the User is a BenCo:
    @Around("benCo()")
    public Object verifyBenCo(ProceedingJoinPoint pjp) throws Throwable {
        session = getSession(pjp);
        if(session == null || session.getAttribute(SessionFields.LOGGED_USER) == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        EmployeeDto loggedUser = session.getAttribute(SessionFields.LOGGED_USER);
        if(loggedUser.getEmployeeType() != EmployeeType.BENCO) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        }
        return pjp.proceed();
    }

    // Utility method to get the WebSession:
    private WebSession getSession(ProceedingJoinPoint pjp) {
        return (WebSession) Arrays.stream(pjp.getArgs())
                .filter(arg -> arg instanceof WebSession)
                .findFirst()
                .orElse(null);
    }

    // @LoggedIn:
    @Pointcut("@annotation(com.infy.aspects.LoggedIn)")
    public void loggedIn() {/* Hook for @LoggedIn */}

    // @CurrentUser:
    @Pointcut("@annotation(com.infy.aspects.CurrentUser)")
    public void currentUser() {/* Hook for @CurrentUser */}

    // @BenCo:
    @Pointcut("@annotation(com.infy.aspects.BenCo)")
    public void benCo() {/* Hook for @BenCo */}
}
