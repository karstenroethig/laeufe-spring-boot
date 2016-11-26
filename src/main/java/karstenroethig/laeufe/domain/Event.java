package karstenroethig.laeufe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import karstenroethig.laeufe.domain.enums.EventStatusEnum;
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
public class Event {
	
	@Column(
		length = 18,
		nullable = false,
		unique = true
	)
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Id
	private Long id;
	
	@JoinColumn( name = "organizer_id" )
	@ManyToOne( optional = false )
	private Organizer organizer;
	
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		mappedBy = "event"
	)
	private List<Race> races = new ArrayList<Race>();
	
	@Column(
		length = 255,
		nullable = false
	)
	private String name;
	
	@Column(
		name = "start_date",
		nullable = false
	)
	@Temporal( TemporalType.DATE )
	private Date startDate;
	
	@Column( name = "end_date" )
	@Temporal( TemporalType.DATE )
	private Date endDate;
	
	@Column(
		length = 1024,
		nullable = false
	)
	private String locationName;
	
	@JoinColumn( name = "location_country_id" )
	@ManyToOne( optional = false )
	private Country locationCountry;
	
	@Column(
		name = "location_latitude",
		precision = 9,
		scale = 6
	)
	private BigDecimal locationLatitude;
	
	@Column(
		name = "location_longitude",
		precision = 9,
		scale = 6
	)
	private BigDecimal locationLongitude;
	
	@Column(
		length = 25,
		nullable = false
	)
	private String distance;
	
	@Column( length = 25 )
	private String racetime;
	
	@Column( length = 25 )
	private String costs;
	
	@Column( nullable = false )
	private Integer status;
	
	public void addRace( Race race ) {
		race.setEvent( this );
		
		races.add( race );
	}
	
	public boolean removeRace( Race race ) {
		race.setEvent( null );
		
		return races.remove( race );
	}
	
	@Transient
	public EventStatusEnum getStatusEnum() {
		return EventStatusEnum.getStatusForKey( status );
	}
	
	public void setStatusEnum( EventStatusEnum eventStatusEnum ) {
		
		if( eventStatusEnum != null ) {
			setStatus( eventStatusEnum.getKey() );
		} else {
			setStatus( null );
		}
	}
}
