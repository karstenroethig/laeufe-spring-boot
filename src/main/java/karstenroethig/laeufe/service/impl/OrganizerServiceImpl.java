package karstenroethig.laeufe.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import karstenroethig.laeufe.domain.Organizer;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.OrganizerDto;
import karstenroethig.laeufe.repository.OrganizerRepository;
import karstenroethig.laeufe.service.OrganizerService;
import karstenroethig.laeufe.service.exceptions.OrganizerAlreadyExistsException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OrganizerServiceImpl implements OrganizerService {

    @Autowired
    protected OrganizerRepository organizerRepository;

    @Override
    public OrganizerDto newOrganizer() {

        OrganizerDto organizerDto = new OrganizerDto();

        organizerDto.setArchived( Boolean.FALSE );

        return organizerDto;
    }

    @Override
    public OrganizerDto saveOrganizer( OrganizerDto organizerDto ) throws OrganizerAlreadyExistsException {

    	// TODO code me!
        if( StringUtils.equals( organizerDto.getName(), "err" ) ) {
            throw new OrganizerAlreadyExistsException();
        }

        Organizer organizer = new Organizer();

        organizer = DtoTransformer.merge( organizer, organizerDto );

        return DtoTransformer.transform( organizerRepository.save( organizer ) );
    }

    @Override
    public Boolean deleteOrganizer( Long organizerId ) {

        Organizer temp = organizerRepository.findOne( organizerId );

        if( temp != null ) {
        	organizerRepository.delete( temp );

            return true;
        }

        return false;
    }

    @Override
    public OrganizerDto editOrganizer( OrganizerDto organizerDto ) throws OrganizerAlreadyExistsException {

    	// TODO code me!
        if( StringUtils.equals( organizerDto.getName(), "err" ) ) {
            throw new OrganizerAlreadyExistsException();
        }

        Organizer organizer = organizerRepository.findOne( organizerDto.getId() );

        organizer = DtoTransformer.merge( organizer, organizerDto );

        return DtoTransformer.transform( organizerRepository.save( organizer ) );
    }

    @Override
    public List<OrganizerDto> getAllOrganizers() {

        Iterable<Organizer> itr = organizerRepository.findAll();

        List<OrganizerDto> organizers = new ArrayList<OrganizerDto>();

        itr.forEach( new Consumer<Organizer>() {
                @Override
                public void accept( Organizer organizer ) {
                	organizers.add( DtoTransformer.transform( organizer ) );
                }
            } );

        // TODO there has to be a better way for sorting
        Collections.sort( organizers, new Comparator<OrganizerDto>() {
                @Override
                public int compare( OrganizerDto o1, OrganizerDto o2 ) {
                    return o1.getName().compareTo( o2.getName() );
                }
            } );

        return organizers;
    }

    @Override
    public OrganizerDto findOrganizer( Long organizerId ) {
        return DtoTransformer.transform( organizerRepository.findOne( organizerId ) );
    }
}
