package karstenroethig.laeufe.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import karstenroethig.laeufe.dto.CountryDto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class CountryFormatter implements Formatter<CountryDto>
{
	@Override
	public String print( CountryDto country, Locale locale )
	{
		if ( ( country == null ) || ( country.getId() == null ) )
		{
			return StringUtils.EMPTY;
		}

		return country.getId().toString();
	}

	@Override
	public CountryDto parse( String id, Locale locale ) throws ParseException
	{
		if ( StringUtils.isBlank( id ) && ( StringUtils.isNumeric( id ) == false ) )
		{
			return null;
		}

		CountryDto country = new CountryDto();

		country.setId( Long.parseLong( id ) );

		return country;
	}
}
