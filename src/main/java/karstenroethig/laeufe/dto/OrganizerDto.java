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
public class OrganizerDto
{
	private Long id;

	@NotNull
	@Size(
		min = 1,
		max = 255
	)
	private String name;

	@Size( max = 1024 )
	private String description;

	@Size( max = 255 )
	private String logoFile;

	@NotNull
	private Boolean archived;
}
