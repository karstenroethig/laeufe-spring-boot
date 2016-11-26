package karstenroethig.laeufe.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.domain.Event;
import karstenroethig.laeufe.domain.Race;
import karstenroethig.laeufe.domain.enums.EventStatusEnum;
import karstenroethig.laeufe.domain.enums.RaceStatusEnum;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.EventFullDto;
import karstenroethig.laeufe.dto.EventListDto;
import karstenroethig.laeufe.dto.RaceDto;
import karstenroethig.laeufe.dto.info.DashboardInfoDto;
import karstenroethig.laeufe.repository.CountryRepository;
import karstenroethig.laeufe.repository.EventRepository;
import karstenroethig.laeufe.repository.OrganizerRepository;
import karstenroethig.laeufe.service.EventService;


@Service
@Transactional
public class EventServiceImpl implements EventService {
	
	private static final BigDecimal DISTANCE_DIVISOR = new BigDecimal( 1000 );

    @Autowired
    protected EventRepository eventRepository;

    @Autowired
    protected OrganizerRepository organizerRepository;

    @Autowired
    protected CountryRepository countryRepository;

    @Override
    public EventFullDto newEvent() {

        EventFullDto eventDto = new EventFullDto();

        return eventDto;
    }

    @Override
    public EventFullDto saveEvent( EventFullDto eventDto ) {

        Event event = new Event();

        event = merge( event, eventDto );

        return transformFull( eventRepository.save( event ) );
    }

    @Override
    public Boolean deleteEvent( Long eventId ) {

        Event temp = eventRepository.findOne( eventId );

        if( temp != null ) {
            eventRepository.delete( temp );

            return true;
        }

        return false;
    }

    @Override
    public EventFullDto editEvent( EventFullDto eventDto ) {

        Event event = eventRepository.findOne( eventDto.getId() );

        event = merge( event, eventDto );

        return transformFull( eventRepository.save( event ) );
    }

    @Override
    public List<EventListDto> getAllEvents() {

        Iterable<Event> itr = eventRepository.findAll();

        List<EventListDto> events = new ArrayList<EventListDto>();

        itr.forEach( new Consumer<Event>() {
                @Override
                public void accept( Event event ) {
                    events.add( transformList( event ) );
                }
            } );

        // TODO there has to be a better way for sorting
        Collections.sort( events, new Comparator<EventListDto>() {
                @Override
                public int compare( EventListDto o1, EventListDto o2 ) {
                    return -1 * o1.getEventPeriod().getStartDate().compareTo( o2.getEventPeriod().getStartDate() );
                }
            } );

        return events;
    }

    @Override
    public EventFullDto findEvent( Long eventId ) {
        return transformFull( eventRepository.findOne( eventId ) );
    }

    private Event merge( Event event, EventListDto eventDto ) {

        if( ( event == null ) || ( eventDto == null ) ) {
            return null;
        }

        if( eventDto.getOrganizer() != null ) {
            event.setOrganizer( organizerRepository.findOne( eventDto.getOrganizer().getId() ) );
        }

        event.setName( eventDto.getName() );
        event.setStartDate( eventDto.getEventPeriod().getStartDate() );
        event.setEndDate( eventDto.getEventPeriod().getEndDate() );
        event.setLocationName( eventDto.getLocationName() );

        if( eventDto.getLocationCountry() != null ) {
            event.setLocationCountry( countryRepository.findOne( eventDto.getLocationCountry().getId() ) );
        }

        event.setLocationLatitude( eventDto.getLocationLatitude() );
        event.setLocationLongitude( eventDto.getLocationLongitude() );
        event.setDistance( eventDto.getDistance() );
        event.setRacetime( eventDto.getRacetime() );
        event.setCosts( eventDto.getCosts() );
        event.setStatusEnum( eventDto.getStatus() );

        return event;
    }

    private EventListDto transformList( Event event ) {

        if( event == null ) {
            return null;
        }

        EventListDto eventDto = new EventListDto();

        eventDto.setId( event.getId() );
        eventDto.setOrganizer( DtoTransformer.transform( event.getOrganizer() ) );
        eventDto.setName( event.getName() );
        eventDto.getEventPeriod().setStartDate( event.getStartDate() );
        eventDto.getEventPeriod().setEndDate( event.getEndDate() );
        eventDto.setLocationName( event.getLocationName() );
        eventDto.setLocationCountry( DtoTransformer.transform( event.getLocationCountry() ) );
        eventDto.setLocationLatitude( event.getLocationLatitude() );
        eventDto.setLocationLongitude( event.getLocationLongitude() );
        eventDto.setDistance( event.getDistance() );
        eventDto.setRacetime( event.getRacetime() );
        eventDto.setCosts( event.getCosts() );
        eventDto.setStatus( event.getStatusEnum() );
        eventDto.setRemainingDays( calcRemainingDays( event.getStartDate() ) );

        return eventDto;
    }

    private EventFullDto transformFull( Event event ) {

        if( event == null ) {
            return null;
        }

        EventFullDto eventDto = new EventFullDto();

        eventDto.setId( event.getId() );
        eventDto.setOrganizer( DtoTransformer.transform( event.getOrganizer() ) );
        eventDto.setName( event.getName() );
        eventDto.getEventPeriod().setStartDate( event.getStartDate() );
        eventDto.getEventPeriod().setEndDate( event.getEndDate() );
        eventDto.setLocationName( event.getLocationName() );
        eventDto.setLocationCountry( DtoTransformer.transform( event.getLocationCountry() ) );
        eventDto.setLocationLatitude( event.getLocationLatitude() );
        eventDto.setLocationLongitude( event.getLocationLongitude() );
        eventDto.setDistance( event.getDistance() );
        eventDto.setRacetime( event.getRacetime() );
        eventDto.setCosts( event.getCosts() );
        eventDto.setStatus( event.getStatusEnum() );
        eventDto.setRemainingDays( calcRemainingDays( event.getStartDate() ) );
        
        List<Race> races = event.getRaces();
        
        if( races != null && races.isEmpty() == false ) {
        	
        	for( Race race : races ) {
        		eventDto.addRace( transform( race ) );
        	}
        }

        return eventDto;
    }
    
    private RaceDto transform( Race race ) {
    	
    	if( race == null ) {
    		return null;
    	}
    	
    	RaceDto raceDto = new RaceDto();
    	
    	raceDto.setId( race.getId() );
    	raceDto.setStartNumber( race.getStartNumber() );
    	raceDto.setStartTime( race.getStartTime() );
    	
    	if( race.getDistance() != null ) {
    		raceDto.setDistance( new BigDecimal( race.getDistance() ).divide( DISTANCE_DIVISOR ) );
    	}
    	
    	raceDto.setRacetime( race.getRacetime() );
    	raceDto.setTeam( race.getTeam() );
    	raceDto.setNote( race.getNote() );
    	raceDto.setStatus( race.getStatusEnum() );
    	
    	return raceDto;
    }
    
    @Override
	public DashboardInfoDto createDashboradStatistics() {
		
		int totalRaces = 0;
		int totalRacesSuccess = 0;
		int totalRacesFailed = 0;
		int totalRacesToughMudder = 0;
		int totalRacesXletix = 0;
		Set<Country> countries = new HashSet<>();
		int longestDistance = 0;
		int totalDistance = 0;
		
		List<EventListDto> upcomingEvents = new ArrayList<>();
		
		Iterator<Event> itr = eventRepository.findAll().iterator();
		
		while( itr.hasNext() ) {

			Event event = itr.next();
			EventStatusEnum eventStatus = event.getStatusEnum();
			
			if( eventStatus == EventStatusEnum.PLANED || eventStatus == EventStatusEnum.REGISTERED ) {
				upcomingEvents.add( transformList( event ) );
			}

			for( Race race : event.getRaces() ) {
				
				RaceStatusEnum raceStatus = race.getStatusEnum();
				
				if( raceStatus != RaceStatusEnum.COMPLETED && raceStatus != RaceStatusEnum.FAILED ) {
					continue;
				}
				
				totalRaces++;
				
				int raceDistance = ( race.getDistance() != null ? race.getDistance() : 0 );
				
				if( raceDistance > longestDistance ) {
					longestDistance = raceDistance;
				}
				
				totalDistance += raceDistance;
				
				if( raceStatus == RaceStatusEnum.COMPLETED ) {
					
					totalRacesSuccess++;
					
					String organizerName = event.getOrganizer().getName();
					
					if( StringUtils.equals( organizerName, "Tough Mudder" ) ) {
						totalRacesToughMudder++;
					}else if( StringUtils.equals( organizerName, "XLETIX Challenge" ) ) {
						totalRacesXletix++;
					}
					
					countries.add( event.getLocationCountry() );

				} else if( raceStatus == RaceStatusEnum.FAILED ) {
					totalRacesFailed++;
				}
			}
		}
		

		// TODO there has to be a better way for sorting
		Collections.sort( upcomingEvents, new Comparator<EventListDto>() {
			@Override
			public int compare( EventListDto o1, EventListDto o2 ) {
				return 1 * o1.getEventPeriod().getStartDate().compareTo( o2.getEventPeriod().getStartDate() );
			}
		} );
		
		DashboardInfoDto stats = new DashboardInfoDto();
		
		stats.setTotalRaces( totalRaces );
		stats.setTotalRacesSuccess( totalRacesSuccess );
		stats.setTotalRacesFailed( totalRacesFailed );
		stats.setTotalRacesToughMudder( totalRacesToughMudder );
		stats.setTotalRacesXletix( totalRacesXletix );
		stats.setTotalCountries( countries.size() );
		stats.setLongestDistance( new BigDecimal( longestDistance ).divide( DISTANCE_DIVISOR ) );
		stats.setTotalDistance( new BigDecimal( totalDistance ).divide( DISTANCE_DIVISOR ) );
		stats.setUpcomingEvents( upcomingEvents );
		
		return stats;
	}
	
	private static long calcRemainingDays( Date startDate ) {
		
		if( startDate != null ) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime( startDate );
			LocalDate startDateLocal = LocalDate.of(
					cal.get( Calendar.YEAR ),
					cal.get( Calendar.MONTH ) + 1,
					cal.get( Calendar.DAY_OF_MONTH ) );
			
			return ChronoUnit.DAYS.between( LocalDate.now(), startDateLocal );
		}
		
		return 0;
	}
}
