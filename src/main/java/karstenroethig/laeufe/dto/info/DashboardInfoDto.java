package karstenroethig.laeufe.dto.info;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import karstenroethig.laeufe.dto.EventListDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardInfoDto
{
	private int totalRaces = 0;
	private int totalRacesSuccess = 0;
	private int totalRacesFailed = 0;
	private int totalRacesDnp = 0;
	private int totalObstacleRaces = 0;
	private int totalObstacleRacesSuccess = 0;
	private int totalObstacleRacesFailed = 0;
	private int totalObstacleRacesDnp = 0;
	private int totalCountries = 0;

	private BigDecimal longestDistance = new BigDecimal( 0 );
	private BigDecimal totalDistance = new BigDecimal( 0 );

	private List<EventListDto> upcomingEvents = new ArrayList<>();
}