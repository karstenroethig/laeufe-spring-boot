package karstenroethig.laeufe.dto.api;

import java.util.HashMap;
import java.util.Map;

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
public class CategorySpreadingForYearApiDto
{
	private int year;
	private Map<String, Integer> amountPerCategory = new HashMap<>();

	public void setUpCategory(String category)
	{
		if (!amountPerCategory.containsKey(category))
		{
			amountPerCategory.put(category, 0);
		}
	}

	public void incrementAmount(String category)
	{
		if (amountPerCategory.containsKey(category))
		{
			Integer oldAmount = amountPerCategory.get(category);
			amountPerCategory.put(category, oldAmount + 1);
		}
		else
		{
			amountPerCategory.put(category, 1);
		}
	}
}
