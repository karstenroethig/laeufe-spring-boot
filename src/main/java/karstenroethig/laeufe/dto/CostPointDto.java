package karstenroethig.laeufe.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CostPointDto
{
	private Long id;

	@NotNull
	private Integer sequence;

	@NotNull
	@Size(max = 255)
	private String description;

	@NotNull
	@Digits(
		integer = 10,
		fraction = 2
	)
	private BigDecimal amount;

	@Digits(
		integer = 10,
		fraction = 2
	)
	private BigDecimal amountForeignCurrency;

	@Size(max = 20)
	private String foreignCurrency;
}
