package karstenroethig.laeufe.dto;

import java.math.BigDecimal;
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

	private List<CostPointDto> costPoints = new ArrayList<>();
	private CostPointDto costPointsTotal = new CostPointDto();

	public void addRace(RaceDto race)
	{
		races.add(race);
	}

	public void addCostPoint(CostPointDto costPoint)
	{
		costPoints.add(costPoint);

		if (costPoint.getAmount() != null)
		{
			if (costPointsTotal.getAmount() == null)
			{
				costPointsTotal.setAmount(new BigDecimal(0.0));
			}

			costPointsTotal.setAmount(costPointsTotal.getAmount().add(costPoint.getAmount()));
		}
	}
}
