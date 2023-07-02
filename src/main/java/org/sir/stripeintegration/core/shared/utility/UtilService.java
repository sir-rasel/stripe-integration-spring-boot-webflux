package org.sir.stripeintegration.core.shared.utility;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Slf4j
@Service
@AllArgsConstructor
public class UtilService {
    private Validator validator;

    public <T> String validation(T t) {
        log.info(t.toString());
        Iterator<ConstraintViolation<T>> it = validator.validate(t).iterator();
        String message = "";
        while (it.hasNext()) {
            ConstraintViolation<T> constraintViolation = it.next();
            log.info("Error message : {}", constraintViolation.getMessage());
            message += constraintViolation.getMessage() + "\n";
        }
        return message;
    }
}
