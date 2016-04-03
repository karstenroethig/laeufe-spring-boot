package karstenroethig.laeufe.dto.info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryInfoDto {

	private long total;
	private String totalFormated;
	private long used;
	private String usedFormated;
	private long free;
	private long freePercentage;
	private String freeFormated;
}