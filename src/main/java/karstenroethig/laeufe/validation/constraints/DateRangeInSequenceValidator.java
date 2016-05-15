package karstenroethig.laeufe.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import karstenroethig.laeufe.dto.DateRange;

public class DateRangeInSequenceValidator implements ConstraintValidator<DateRangeInSequence, DateRange> {
	
	@Override
	public void initialize( DateRangeInSequence constraintAnnotation ) {
		// Nothing to do
	}
	
	@Override
	public boolean isValid( DateRange value, ConstraintValidatorContext context ) {
		
		if( value == null || value.getStartDate() == null || value.getEndDate() == null ) {
			return true;
		}
		
		return value.getStartDate().before( value.getEndDate() );
	}
}
