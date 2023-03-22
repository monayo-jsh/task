package com.task.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.task.annotation.ValidEnum.EnumValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    String message() default "invalid enum. this is not supported.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

        @Override
        public void initialize(ValidEnum constraintAnnotation) {

        }

        @Override
        public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
            //NotNullEnum 어노테이션 정상 동작을 위해 다음과 같이 처리
            //해당 Enum 객체 내 @JsonCreator 정의
            //parse value 없는 경우 null 반환
            return Objects.nonNull(value);
        }
    }
}
