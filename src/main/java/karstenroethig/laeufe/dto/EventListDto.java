package karstenroethig.laeufe.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import karstenroethig.laeufe.domain.enums.EventStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventListDto
{
	private Long id;

	@NotNull
	private OrganizerDto organizer;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String name;

	@NotNull
	@Valid
	private DateRange eventPeriod = new DateRange();

	@NotNull
	@Size(
		min = 1,
		max = 1024
	)
	private String locationName;

	@NotNull
	private CountryDto locationCountry;

	@Digits(
		integer = 9,
		fraction = 6
	)
	private BigDecimal locationLatitude;

	@Digits(
		integer = 9,
		fraction = 6
	)
	private BigDecimal locationLongitude;

	@NotNull
	@Size(
		min = 1,
		max = 25
	)
	private String distance;

	@Size( max = 25 )
	private String racetime;

	@NotNull
	@Digits(
		integer = 10,
		fraction = 2
	)
	private BigDecimal costs;

	@NotNull
	private EventStatusEnum status;

	private long remainingDays;
}
