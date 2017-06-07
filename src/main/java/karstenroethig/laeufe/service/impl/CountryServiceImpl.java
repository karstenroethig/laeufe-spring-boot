package karstenroethig.laeufe.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.dto.CountryDto;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.repository.CountryRepository;
import karstenroethig.laeufe.service.CountryService;
import karstenroethig.laeufe.service.exceptions.CountryAlreadyExistsException;


@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    protected CountryRepository countryRepository;

    @Override
    public CountryDto newCountry() {

        CountryDto countryDto = new CountryDto();

        countryDto.setArchived( Boolean.FALSE );

        return countryDto;
    }

    @Override
    public CountryDto saveCountry( CountryDto countryDto ) throws CountryAlreadyExistsException {

        List<Country> existingCountries = countryRepository.findByNameIgnoreCase(
                StringUtils.trim( countryDto.getName() ) );

        if( existingCountries != null && existingCountries.isEmpty() == false ) {
            throw new CountryAlreadyExistsException();
        }

        Country country = new Country();

        country = DtoTransformer.merge( country, countryDto );

        return DtoTransformer.transform( countryRepository.save( country ) );
    }

    @Override
    public Boolean deleteCountry( Long countryId ) {

        Country temp = countryRepository.findOne( countryId );

        if( temp != null ) {
            countryRepository.delete( temp );

            return true;
        }

        return false;
    }

    @Override
    public CountryDto editCountry( CountryDto countryDto ) throws CountryAlreadyExistsException {

        List<Country> existingCountries = countryRepository.findByNameIgnoreCase(
                StringUtils.trim( countryDto.getName() ) );

        if( existingCountries != null && existingCountries.isEmpty() == false
                && existingCountries.get( 0 ).getId().equals( countryDto.getId() ) == false ) {
            throw new CountryAlreadyExistsException();
        }

        Country country = countryRepository.findOne( countryDto.getId() );

        country = DtoTransformer.merge( country, countryDto );

        return DtoTransformer.transform( countryRepository.save( country ) );
    }

    @Override
    public CountryDto findCountry( Long countryId ) {
        return DtoTransformer.transform( countryRepository.findOne( countryId ) );
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return transformCountries( countryRepository.findAll() );
    }

    @Override
    public List<CountryDto> getAllArchivedCountries() {
        return transformCountries( countryRepository.findByArchived( true ) );
    }

    @Override
    public List<CountryDto> getAllUnarchivedCountries() {
        return transformCountries( countryRepository.findByArchived( false ) );
    }

    private List<CountryDto> transformCountries( List<Country> countrys ) {
        return transformCountries( countrys.stream() );
    }

    private List<CountryDto> transformCountries( Iterable<Country> countrys ) {
        return transformCountries( StreamSupport.stream( countrys.spliterator(), false ) );
    }

    private List<CountryDto> transformCountries( Stream<Country> countrysStream ) {

        List<CountryDto> transformedCountries = countrysStream
            .map( DtoTransformer::transform )
            .sorted( Comparator.comparing( CountryDto::getName ) )
            .collect( Collectors.toList() );

        return transformedCountries;
    }
}
