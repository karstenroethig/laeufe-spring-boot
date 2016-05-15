package karstenroethig.laeufe.service;

import java.util.Collection;

import karstenroethig.laeufe.dto.OrganizerDto;
import karstenroethig.laeufe.service.exceptions.OrganizerAlreadyExistsException;


public interface OrganizerService {
	
	public OrganizerDto newOrganizer();

    public OrganizerDto saveOrganizer( OrganizerDto organizerDto ) throws OrganizerAlreadyExistsException;

    public Boolean deleteOrganizer( Long organizerId );

    public OrganizerDto editOrganizer( OrganizerDto organizerDto ) throws OrganizerAlreadyExistsException;

    public OrganizerDto findOrganizer( Long organizerId );

    public Collection<OrganizerDto> getAllOrganizers();
    
    public Collection<OrganizerDto> getAllArchivedOrganizers();
    
    public Collection<OrganizerDto> getAllUnarchivedOrganizers();
}
