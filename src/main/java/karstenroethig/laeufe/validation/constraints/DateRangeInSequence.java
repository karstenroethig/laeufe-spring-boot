package karstenroethig.laeufe.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint( validatedBy = { DateRangeInSequenceValidator.class } )
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
public @interface DateRangeInSequence
{
	String message() default "{validator.dateRange.notInSequence}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
