package karstenroethig.laeufe.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventFullDto extends EventListDto
{
	private List<RaceDto> races = new ArrayList<>();

	public void addRace( RaceDto race )
	{
		races.add( race );
	}
}
