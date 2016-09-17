package karstenroethig.laeufe.domain;

import karstenroethig.laeufe.domain.enums.EventStatusEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

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
		length = 255,
		nullable = false
	)
	private String distance;
	
	private String racetime;
	
	private String costs;
	
	@Column( nullable = false )
	private Integer status;
	
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
