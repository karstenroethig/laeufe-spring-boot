package karstenroethig.laeufe.dto.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrganizerPreferenceApiDto
{
	private String organizer;
	private int races = 0;

	public void incrementRaces()
	{
		races++;
	}
}
