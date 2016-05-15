package karstenroethig.laeufe.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import karstenroethig.laeufe.domain.Event;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.EventDto;
import karstenroethig.laeufe.repository.EventRepository;
import karstenroethig.laeufe.repository.OrganizerRepository;
import karstenroethig.laeufe.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    protected EventRepository eventRepository;

    @Autowired
    protected OrganizerRepository organizerRepository;

    @Override
    public EventDto newEvent() {

        EventDto eventDto = new EventDto();

        return eventDto;
    }

    @Override
    public EventDto saveEvent( EventDto eventDto ) {
    	
        Event event = new Event();

        event = merge( event, eventDto );

        return transform( eventRepository.save( event ) );
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
    public EventDto editEvent( EventDto eventDto ) {

        Event event = eventRepository.findOne( eventDto.getId() );

        event = merge( event, eventDto );

        return transform( eventRepository.save( event ) );
    }

    @Override
    public List<EventDto> getAllEvents() {

        Iterable<Event> itr = eventRepository.findAll();

        List<EventDto> events = new ArrayList<EventDto>();

        itr.forEach( new Consumer<Event>() {
                @Override
                public void accept( Event event ) {
                	events.add( transform( event ) );
                }
            } );

        // TODO there has to be a better way for sorting
        Collections.sort( events, new Comparator<EventDto>() {
                @Override
                public int compare( EventDto o1, EventDto o2 ) {
                    return -1 * o1.getEventPeriod().getStartDate().compareTo( o2.getEventPeriod().getStartDate() );
                }
            } );

        return events;
    }

    @Override
    public EventDto findEvent( Long eventId ) {
        return transform( eventRepository.findOne( eventId ) );
    }
        
    private Event merge( Event event, EventDto eventDto ) {

        if( ( event == null ) || ( eventDto == null ) ) {
            return null;
        }
        
        if( eventDto.getOrganizer() != null ) {
        	event.setOrganizer( organizerRepository.findOne( eventDto.getOrganizer().getId() ) );
        }
        
        event.setName( eventDto.getName() );
        event.setStartDate( eventDto.getEventPeriod().getStartDate() );
        event.setEndDate( eventDto.getEventPeriod().getEndDate() );
        event.setLocation( eventDto.getLocation() );
        event.setDistance( eventDto.getDistance() );
        event.setStatusEnum( eventDto.getStatus() );

        return event;
    }

    private EventDto transform( Event event ) {

        if( event == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setId( event.getId() );
        eventDto.setOrganizer( DtoTransformer.transform( event.getOrganizer() ) );
        eventDto.setName( event.getName() );
        eventDto.getEventPeriod().setStartDate( event.getStartDate() );
        eventDto.getEventPeriod().setEndDate( event.getEndDate() );
        eventDto.setLocation( event.getLocation() );
        eventDto.setDistance( event.getDistance() );
        eventDto.setStatus( event.getStatusEnum() );

        return eventDto;
    }
}
