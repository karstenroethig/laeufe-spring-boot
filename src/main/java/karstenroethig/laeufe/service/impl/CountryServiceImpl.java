package karstenroethig.laeufe.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;

import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.dto.CountryDto;
import karstenroethig.laeufe.repository.CountryRepository;
import karstenroethig.laeufe.service.CountryService;
import karstenroethig.laeufe.service.exceptions.CountryAlreadyExistsException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    
    private List<CountryDto> transformCountries( Iterable<Country> countrys ) {
    	
    	List<CountryDto> transformedCountries = new ArrayList<CountryDto>();
    	
    	countrys.forEach( new Consumer<Country>() {
    		@Override
    		public void accept( Country country ) {
    			transformedCountries.add( DtoTransformer.transform( country ) );
    		}
    	} );
    	
    	// TODO there has to be a better way for sorting
    	Collections.sort( transformedCountries, new Comparator<CountryDto>() {
            @Override
            public int compare( CountryDto o1, CountryDto o2 ) {
                return o1.getName().compareTo( o2.getName() );
            }
        });
    	
    	return transformedCountries;
    }
}
