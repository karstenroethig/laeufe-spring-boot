package karstenroethig.laeufe.dto;

import javax.validation.Valid;
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
public class EventDto {
	
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
	private String location;
	
	@NotNull
	@Size(
			min = 1,
			max = 255
		)
	private String distance;
	
	private String racetime;
	
	private String costs;
	
	@NotNull
	private EventStatusEnum status;
}
