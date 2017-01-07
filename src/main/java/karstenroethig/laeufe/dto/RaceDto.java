package karstenroethig.laeufe.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import karstenroethig.laeufe.domain.enums.RaceStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class RaceDto {
	
	private Long id;
	
	@Size( max = 25 )
	private String startNumber;
	
	private LocalDateTime startTime;
	
	private BigDecimal distance;
	
	@Size( max = 25 )
	private String racetime;
	
	@Size( max = 255 )
	private String team;
	
	@Size( max = 1024 )
	private String note;
	
	@NotNull
	private RaceStatusEnum status;
}
