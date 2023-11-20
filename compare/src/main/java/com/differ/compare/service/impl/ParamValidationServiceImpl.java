package com.differ.compare.service.impl;

import com.differ.compare.service.ParamValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/9 15:24
 */

@Service
public class ParamValidationServiceImpl implements ParamValidationService {
    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Override
    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
    }


    public <T> String validateObject(T object) {
        StringBuffer sb = new StringBuffer();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        violations.forEach(
                tConstraintViolation -> {
                    if (!tConstraintViolation.getMessage().isEmpty()){
                        sb.append(tConstraintViolation.getMessage().toString());
                    }
                }
        );
        return sb.toString();
    }


}
