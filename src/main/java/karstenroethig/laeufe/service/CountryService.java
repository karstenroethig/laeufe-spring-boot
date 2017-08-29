package karstenroethig.laeufe.service;

import java.util.Collection;

import karstenroethig.laeufe.dto.CountryDto;
import karstenroethig.laeufe.service.exceptions.CountryAlreadyExistsException;

public interface CountryService
{
	public CountryDto newCountry();

	public CountryDto saveCountry( CountryDto countryDto ) throws CountryAlreadyExistsException;

	public Boolean deleteCountry( Long countryId );

	public CountryDto editCountry( CountryDto countryDto ) throws CountryAlreadyExistsException;

	public CountryDto findCountry( Long countryId );

	public Collection<CountryDto> getAllCountries();

	public Collection<CountryDto> getAllArchivedCountries();

	public Collection<CountryDto> getAllUnarchivedCountries();
}
