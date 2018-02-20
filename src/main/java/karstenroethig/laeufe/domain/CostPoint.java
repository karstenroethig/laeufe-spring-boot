package karstenroethig.laeufe.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(
	name = "Cost_Point",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"id"})
	}
)
public class CostPoint
{
	@Column(
		length = 18,
		nullable = false,
		unique = true
	)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "event_id")
	@ManyToOne(optional = false)
	private Event event;

	@Column(nullable = false)
	private Integer sequence;

	@Column(
		length = 255,
		nullable = false
	)
	private String description;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(
		name = "amount_foreign_currency",
		nullable = true
	)
	private BigDecimal amountForeignCurrency;

	@Column(
		name = "foreign_currency",
		length = 20,
		nullable = true
	)
	private String foreignCurrency;
}
