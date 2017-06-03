package karstenroethig.laeufe.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.laeufe.domain.Organizer;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.OrganizerDto;
import karstenroethig.laeufe.repository.OrganizerRepository;
import karstenroethig.laeufe.service.OrganizerService;
import karstenroethig.laeufe.service.exceptions.OrganizerAlreadyExistsException;


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

        List<Organizer> existingOrganizers = organizerRepository.findByNameIgnoreCase(
                StringUtils.trim( organizerDto.getName() ) );

        if( existingOrganizers != null && existingOrganizers.isEmpty() == false ) {
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

        List<Organizer> existingOrganizers = organizerRepository.findByNameIgnoreCase(
                StringUtils.trim( organizerDto.getName() ) );

        if( existingOrganizers != null && existingOrganizers.isEmpty() == false
                && existingOrganizers.get( 0 ).getId().equals( organizerDto.getId() ) == false ) {
            throw new OrganizerAlreadyExistsException();
        }

        Organizer organizer = organizerRepository.findOne( organizerDto.getId() );

        organizer = DtoTransformer.merge( organizer, organizerDto );

        return DtoTransformer.transform( organizerRepository.save( organizer ) );
    }

    @Override
    public OrganizerDto findOrganizer( Long organizerId ) {
        return DtoTransformer.transform( organizerRepository.findOne( organizerId ) );
    }

    @Override
    public List<OrganizerDto> getAllOrganizers() {
        return transformOrganizers( organizerRepository.findAll() );
    }

    @Override
    public List<OrganizerDto> getAllArchivedOrganizers() {
        return transformOrganizers( organizerRepository.findByArchived( true ) );
    }

    @Override
    public List<OrganizerDto> getAllUnarchivedOrganizers() {
        return transformOrganizers( organizerRepository.findByArchived( false ) );
    }

    private List<OrganizerDto> transformOrganizers( List<Organizer> organizers ) {
        return transformOrganizers( organizers.stream() );
    }

    private List<OrganizerDto> transformOrganizers( Iterable<Organizer> organizers ) {
        return transformOrganizers( StreamSupport.stream( organizers.spliterator(), false ) );
    }

    private List<OrganizerDto> transformOrganizers( Stream<Organizer> organizersStream ) {

        List<OrganizerDto> transformedOrganizers = organizersStream
            .map( DtoTransformer::transform )
            .sorted( ( o1, o2 ) -> o1.getName().compareTo( o2.getName() ) )
            .collect( Collectors.toList() );

        return transformedOrganizers;
    }
}
