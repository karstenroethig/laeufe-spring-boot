package karstenroethig.laeufe.dto.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationApiDto
{
	private String name;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private List<String> events = new ArrayList<>();

	public void addEvent( String event ) {
		events.add( event );
	}
}
