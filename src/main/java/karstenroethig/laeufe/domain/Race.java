package karstenroethig.laeufe.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import karstenroethig.laeufe.domain.enums.RaceStatusEnum;
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
    uniqueConstraints = {
        @UniqueConstraint( columnNames = { "id" } )
    }
)
public class Race {
	
	@Column(
		length = 18,
		nullable = false,
		unique = true
	)
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Id
	private Long id;
	
	@JoinColumn( name = "event_id" )
	@ManyToOne( optional = false )
	private Event event;
	
	@Column(
		length = 25,
		name = "start_number"
	)
	private String startNumber;
	
	@Column( name = "start_time" )
	@Temporal( TemporalType.DATE )
	private Date startTime;
	
	@Column( length = 25 )
	private String distance;
	
	@Column( length = 25 )
	private String racetime;

	@Column( length = 255 )
	private String team;

	@Column( length = 1024 )
	private String note;
	
	@Column( nullable = false )
	private Integer status;
	
	@Transient
	public RaceStatusEnum getStatusEnum() {
		return RaceStatusEnum.getStatusForKey( status );
	}
	
	public void setStatusEnum( RaceStatusEnum raceStatusEnum ) {
		
		if( raceStatusEnum != null ) {
			setStatus( raceStatusEnum.getKey() );
		} else {
			setStatus( null );
		}
	}
}
