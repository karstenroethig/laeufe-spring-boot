package karstenroethig.laeufe.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import karstenroethig.laeufe.validation.constraints.DateRangeInSequence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString

@DateRangeInSequence
public class DateRange implements Comparable<DateRange>
{
	@DateTimeFormat( pattern = "dd.MM.yyyy" )
	@NotNull
	private LocalDate startDate;

	@DateTimeFormat( pattern = "dd.MM.yyyy" )
	private LocalDate endDate;

	@Override
	public int compareTo( DateRange other )
	{
		return this.getStartDate().compareTo( other.getStartDate() );
	}
}
