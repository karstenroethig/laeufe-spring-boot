package karstenroethig.laeufe.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import karstenroethig.laeufe.validation.constraints.DateRangeInSequence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@Setter
@ToString

@DateRangeInSequence
public class DateRange {
	
	@DateTimeFormat( pattern = "dd.MM.yyyy" )
	@NotNull
	private Date startDate;
	
	@DateTimeFormat( pattern = "dd.MM.yyyy" )
	private Date endDate;
}
