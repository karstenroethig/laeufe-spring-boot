package karstenroethig.laeufe.controller.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

import karstenroethig.laeufe.dto.OrganizerDto;

public class OrganizerFormatter implements Formatter<OrganizerDto>
{
	@Override
	public String print( OrganizerDto organizer, Locale locale )
	{
		if ( ( organizer == null ) || ( organizer.getId() == null ) )
		{
			return StringUtils.EMPTY;
		}

		return organizer.getId().toString();
	}

	@Override
	public OrganizerDto parse( String id, Locale locale ) throws ParseException
	{
		if ( StringUtils.isBlank( id ) && ( StringUtils.isNumeric( id ) == false ) )
		{
			return null;
		}

		OrganizerDto organizer = new OrganizerDto();

		organizer.setId( Long.parseLong( id ) );

		return organizer;
	}
}
