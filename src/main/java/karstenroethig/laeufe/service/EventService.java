package karstenroethig.laeufe.service;

import java.util.Collection;

import karstenroethig.laeufe.dto.EventFullDto;
import karstenroethig.laeufe.dto.EventListDto;
import karstenroethig.laeufe.dto.api.CountryApiDto;
import karstenroethig.laeufe.dto.api.LocationApiDto;
import karstenroethig.laeufe.dto.info.DashboardInfoDto;

public interface EventService
{
	public EventFullDto newEvent();

	public EventFullDto saveEvent(EventFullDto eventDto);

	public Boolean deleteEvent(Long eventId);

	public EventFullDto editEvent(EventFullDto eventDto);

	public EventFullDto findEvent(Long eventId);

	public Collection<EventListDto> getAllEvents();

	public DashboardInfoDto createDashboradStatistics();

	public Collection<LocationApiDto> findEventLocations();

	public Collection<CountryApiDto> findEventLocationCountries();

	public LocationApiDto findEventLocation(Long eventid);
}
