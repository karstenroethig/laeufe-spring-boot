package karstenroethig.laeufe.service;

import java.util.Collection;

import karstenroethig.laeufe.dto.EventDto;


public interface EventService {
	
	public EventDto newEvent();

    public EventDto saveEvent( EventDto eventDto );

    public Boolean deleteEvent( Long eventId );

    public EventDto editEvent( EventDto eventDto );

    public EventDto findEvent( Long eventId );

    public Collection<EventDto> getAllEvents();
}
