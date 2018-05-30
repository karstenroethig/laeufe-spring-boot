package karstenroethig.laeufe.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.laeufe.domain.Category;
import karstenroethig.laeufe.domain.CostPoint;
import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.domain.Event;
import karstenroethig.laeufe.domain.Organizer;
import karstenroethig.laeufe.domain.Race;
import karstenroethig.laeufe.domain.enums.EventStatusEnum;
import karstenroethig.laeufe.domain.enums.RaceStatusEnum;
import karstenroethig.laeufe.dto.CostPointDto;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.EventFullDto;
import karstenroethig.laeufe.dto.EventListDto;
import karstenroethig.laeufe.dto.RaceDto;
import karstenroethig.laeufe.dto.api.CategorySpreadingForYearApiDto;
import karstenroethig.laeufe.dto.api.CountryApiDto;
import karstenroethig.laeufe.dto.api.LocationApiDto;
import karstenroethig.laeufe.dto.api.OrganizerPreferenceApiDto;
import karstenroethig.laeufe.dto.info.DashboardInfoDto;
import karstenroethig.laeufe.repository.CategoryRepository;
import karstenroethig.laeufe.repository.CountryRepository;
import karstenroethig.laeufe.repository.EventRepository;
import karstenroethig.laeufe.repository.OrganizerRepository;
import karstenroethig.laeufe.service.EventService;

@Service
@Transactional
public class EventServiceImpl implements EventService
{
	@Autowired
	protected EventRepository eventRepository;

	@Autowired
	protected OrganizerRepository organizerRepository;

	@Autowired
	protected CountryRepository countryRepository;

	@Autowired
	protected CategoryRepository categoryRepository;

	@Override
	public EventFullDto newEvent()
	{
		EventFullDto eventDto = new EventFullDto();

		return eventDto;
	}

	@Override
	public EventFullDto saveEvent( EventFullDto eventDto )
	{
		Event event = new Event();

		event = merge( event, eventDto );

		return transformFull( eventRepository.save( event ) );
	}

	@Override
	public Boolean deleteEvent( Long eventId )
	{
		Event temp = eventRepository.findOne( eventId );

		if ( temp != null )
		{
			eventRepository.delete( temp );

			return true;
		}

		return false;
	}

	@Override
	public EventFullDto editEvent( EventFullDto eventDto )
	{
		Event event = eventRepository.findOne( eventDto.getId() );

		event = merge( event, eventDto );

		return transformFull( eventRepository.save( event ) );
	}

	@Override
	public List<EventListDto> getAllEvents()
	{
		List<EventListDto> events = StreamSupport
			.stream( eventRepository.findAll().spliterator(), false )
			.map( ( event ) -> transformList( event ) )
			.sorted( Comparator.comparing( EventListDto::getEventPeriod ).reversed() )
			.collect( Collectors.toList() );

		return events;
	}

	@Override
	public EventFullDto findEvent( Long eventId )
	{
		return transformFull( eventRepository.findOne( eventId ) );
	}

	private Event merge( Event event, EventFullDto eventDto )
	{
		if ( ( event == null ) || ( eventDto == null ) )
		{
			return null;
		}

		if ( eventDto.getOrganizer() != null )
		{
			event.setOrganizer( organizerRepository.findOne( eventDto.getOrganizer().getId() ) );
		}

		event.setName( eventDto.getName() );
		event.setStartDate( eventDto.getEventPeriod().getStartDate() );
		event.setEndDate( eventDto.getEventPeriod().getEndDate() );
		event.setLocationName( eventDto.getLocationName() );

		if ( eventDto.getLocationCountry() != null )
		{
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

	private EventListDto transformList( Event event )
	{
		if ( event == null )
		{
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

	private EventFullDto transformFull( Event event )
	{
		if ( event == null )
		{
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

		if (races != null && races.isEmpty() == false)
		{
			for (Race race : races)
			{
				eventDto.addRace(transform(race));
			}
		}

		List<CostPoint> costPoints = event.getCostPoints();

		if (costPoints != null && costPoints.isEmpty() == false)
		{
			for (CostPoint costPoint : costPoints)
			{
				eventDto.addCostPoint(transform(costPoint));
			}
		}

		return eventDto;
	}

	private RaceDto transform(Race race)
	{
		if (race == null)
		{
			return null;
		}

		RaceDto raceDto = new RaceDto();

		raceDto.setId(race.getId());
		raceDto.setCategory(DtoTransformer.transform(race.getCategory()));
		raceDto.setStartNumber(race.getStartNumber());
		raceDto.setStartTime(race.getStartTime());
		raceDto.setDistance(race.getDistance());
		raceDto.setRacetime(race.getRacetime());
		raceDto.setTeam(race.getTeam());
		raceDto.setNote(race.getNote());
		raceDto.setStatus(race.getStatusEnum());

		return raceDto;
	}

	private CostPointDto transform(CostPoint costPoint)
	{
		if (costPoint == null)
		{
			return null;
		}

		CostPointDto costPointDto = new CostPointDto();

		costPointDto.setId(costPoint.getId());
		costPointDto.setSequence(costPoint.getSequence());
		costPointDto.setDescription(costPoint.getDescription());
		costPointDto.setAmount(costPoint.getAmount());
		costPointDto.setAmountForeignCurrency(costPoint.getAmountForeignCurrency());
		costPointDto.setForeignCurrency(costPoint.getForeignCurrency());

		return costPointDto;
	}

	private LocationApiDto transformLocation(Event event)
	{
		if (event == null)
		{
			return null;
		}

		LocationApiDto locationDto = new LocationApiDto();

		locationDto.setName(event.getLocationName());
		locationDto.setLatitude(event.getLocationLatitude());
		locationDto.setLongitude(event.getLocationLongitude());

		return locationDto;
	}

	@Override
	public DashboardInfoDto createDashboradStatistics()
	{
		int totalRaces = 0;
		int totalRacesSuccess = 0;
		int totalRacesFailed = 0;
		int totalRacesDnp = 0;
		int totalObstacleRaces = 0;
		int totalObstacleRacesSuccess = 0;
		int totalObstacleRacesFailed = 0;
		int totalObstacleRacesDnp = 0;
		Set<Country> countries = new HashSet<>();
		BigDecimal longestDistance = new BigDecimal(0.0);
		BigDecimal totalDistance = new BigDecimal(0.0);

		List<EventListDto> upcomingEvents = new ArrayList<>();

		Iterator<Event> itr = eventRepository.findAll().iterator();

		while (itr.hasNext())
		{
			Event event = itr.next();
			EventStatusEnum eventStatus = event.getStatusEnum();

			if (eventStatus == EventStatusEnum.PLANED || eventStatus == EventStatusEnum.REGISTERED)
			{
				upcomingEvents.add(transformList(event));
			}

			for (Race race : event.getRaces())
			{
				RaceStatusEnum raceStatus = race.getStatusEnum();

				if (raceStatus != RaceStatusEnum.COMPLETED
						&& raceStatus != RaceStatusEnum.FAILED
						&& raceStatus != RaceStatusEnum.DNP)
				{
					continue;
				}

				totalRaces++;

				if (StringUtils.equals(race.getCategory().getName(), "Hindernislauf"))
					totalObstacleRaces++;

				BigDecimal raceDistance = (race.getDistance() != null ? race.getDistance() : BigDecimal.ZERO);

				if (raceDistance.compareTo(longestDistance) > 0)
				{
					longestDistance = raceDistance;
				}

				totalDistance = totalDistance.add(raceDistance);

				if (raceStatus == RaceStatusEnum.COMPLETED)
				{
					totalRacesSuccess++;

					if (StringUtils.equals(race.getCategory().getName(), "Hindernislauf"))
						totalObstacleRacesSuccess++;

					countries.add(event.getLocationCountry());
				}
				else if (raceStatus == RaceStatusEnum.FAILED)
				{
					totalRacesFailed++;

					if (StringUtils.equals(race.getCategory().getName(), "Hindernislauf"))
						totalObstacleRacesFailed++;
				}
				else if (raceStatus == RaceStatusEnum.DNP)
				{
					totalRacesDnp++;

					if (StringUtils.equals(race.getCategory().getName(), "Hindernislauf"))
						totalObstacleRacesDnp++;
				}
			}
		}

		Collections.sort(upcomingEvents, Comparator.comparing(EventListDto::getEventPeriod));

		DashboardInfoDto stats = new DashboardInfoDto();

		stats.setTotalRaces(totalRaces);
		stats.setTotalRacesSuccess(totalRacesSuccess);
		stats.setTotalRacesFailed(totalRacesFailed);
		stats.setTotalRacesDnp(totalRacesDnp);
		stats.setTotalObstacleRaces(totalObstacleRaces);
		stats.setTotalObstacleRacesSuccess(totalObstacleRacesSuccess);
		stats.setTotalObstacleRacesFailed(totalObstacleRacesFailed);
		stats.setTotalObstacleRacesDnp(totalObstacleRacesDnp);
		stats.setTotalCountries(countries.size());
		stats.setLongestDistance(longestDistance);
		stats.setTotalDistance(totalDistance);
		stats.setUpcomingEvents(upcomingEvents);

		return stats;
	}

	private static long calcRemainingDays( LocalDate startDate )
	{
		if ( startDate != null )
		{
			return ChronoUnit.DAYS.between( LocalDate.now(), startDate );
		}

		return 0;
	}

	@Override
	public Collection<LocationApiDto> findEventLocations()
	{
		Map<String, LocationApiDto> locations = new TreeMap<>();

		Iterator<Event> itr = eventRepository.findAll().iterator();

		while (itr.hasNext())
		{
			Event event = itr.next();

			String locationName = event.getLocationName() + ", " + event.getLocationCountry().getName();
			LocationApiDto location;

			if (locations.containsKey(locationName))
			{
				location = locations.get(locationName);
			}
			else
			{
				location = transformLocation(event);
				locations.put(locationName, location);
			}

			location.addEvent(event.getName() + " (" + event.getStartDate().getYear() + ")");
		}

		return locations.values();
	}

	@Override
	public Collection<CountryApiDto> findEventLocationCountries()
	{
		Set<CountryApiDto> countries = new HashSet<>();

		Iterator<Event> itr = eventRepository.findAll().iterator();

		while ( itr.hasNext() )
		{
			Event event = itr.next();

			for ( Race race : event.getRaces() )
			{
				RaceStatusEnum raceStatus = race.getStatusEnum();

				if ( raceStatus == RaceStatusEnum.COMPLETED )
				{
					countries.add( DtoTransformer.transformApi( event.getLocationCountry() ) );
					break;
				}
			}
		}

		return countries;
	}

	@Override
	public LocationApiDto findEventLocation(Long eventId)
	{
		return transformLocation(eventRepository.findOne(eventId));
	}

	@Override
	public Collection<OrganizerPreferenceApiDto> findOrganizerPreferences()
	{
		Map<Long, OrganizerPreferenceApiDto> organizerPreferences = new TreeMap<>();

		Iterator<Event> itr = eventRepository.findAll().iterator();

		while ( itr.hasNext() )
		{
			Event event = itr.next();

			if (event.getStatusEnum() != EventStatusEnum.COMPLETED )
			{
				continue;
			}

			Organizer organizer = event.getOrganizer();
			OrganizerPreferenceApiDto organizerPreference;

			if (organizerPreferences.containsKey(organizer.getId()))
			{
				organizerPreference = organizerPreferences.get(organizer.getId());
			}
			else
			{
				organizerPreference = new OrganizerPreferenceApiDto();
				organizerPreference.setOrganizer(organizer.getName());
				organizerPreferences.put(organizer.getId(), organizerPreference);
			}

			for ( Race race : event.getRaces() )
			{
				RaceStatusEnum raceStatus = race.getStatusEnum();

				if ( raceStatus == RaceStatusEnum.COMPLETED )
				{
					organizerPreference.incrementRaces();
				}
			}
		}

		return organizerPreferences.values();
	}

	@Override
	public Collection<CategorySpreadingForYearApiDto> findCategorySpreadingPerYear()
	{
		Map<Integer, CategorySpreadingForYearApiDto> categorySpreadingPerYearMap = new TreeMap<>();

		Iterator<Event> itr = eventRepository.findAll().iterator();

		while ( itr.hasNext() )
		{
			Event event = itr.next();

			if (event.getStatusEnum() != EventStatusEnum.COMPLETED )
			{
				continue;
			}

			Integer year = event.getStartDate().getYear();
			CategorySpreadingForYearApiDto categorySpreadingForYear;

			if (categorySpreadingPerYearMap.containsKey(year))
			{
				categorySpreadingForYear = categorySpreadingPerYearMap.get(year);
			}
			else
			{
				categorySpreadingForYear = new CategorySpreadingForYearApiDto();
				categorySpreadingForYear.setYear(year);

				for (Category category : categoryRepository.findAll())
				{
					categorySpreadingForYear.setUpCategory(category.getName());
				}

				categorySpreadingPerYearMap.put(year, categorySpreadingForYear);
			}

			for ( Race race : event.getRaces() )
			{
				RaceStatusEnum raceStatus = race.getStatusEnum();

				if ( raceStatus == RaceStatusEnum.COMPLETED )
				{
					categorySpreadingForYear.incrementAmount(race.getCategory().getName());
				}
			}
		}

		return categorySpreadingPerYearMap.values();
	}
}
