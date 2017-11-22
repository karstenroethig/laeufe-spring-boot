package karstenroethig.laeufe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CountryDto
{
	private Long id;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String name;
	
	@NotNull
	@Size(
		min = 2,
		max = 2
	)
	private String code;

	@NotNull
	private Boolean archived;
}
