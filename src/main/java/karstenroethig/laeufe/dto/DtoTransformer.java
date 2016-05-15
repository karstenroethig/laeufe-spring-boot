package karstenroethig.laeufe.dto;


import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.domain.Organizer;

import org.apache.commons.lang3.StringUtils;


public class DtoTransformer {
	
	private DtoTransformer() {}

	/*
	 * =========
	 * Organizer
	 * =========
	 */
	
    public static Organizer merge( Organizer organizer, OrganizerDto organizerDto ) {

        if( ( organizer == null ) || ( organizerDto == null ) ) {
            return null;
        }

        organizer.setName( organizerDto.getName() );
        organizer.setDescription( StringUtils.trimToNull( organizerDto.getDescription() ) );
        organizer.setArchived( organizerDto.getArchived() );

        return organizer;
    }

    public static OrganizerDto transform( Organizer organizer ) {

        if( organizer == null ) {
            return null;
        }

        OrganizerDto organizerDto = new OrganizerDto();

        organizerDto.setId( organizer.getId() );
        organizerDto.setName( organizer.getName() );
        organizerDto.setDescription( StringUtils.trimToNull( organizer.getDescription() ) );
        organizerDto.setArchived( organizer.getArchived() );

        return organizerDto;
    }

	/*
	 * =======
	 * Country
	 * =======
	 */
	
    public static Country merge( Country country, CountryDto countryDto ) {

        if( ( country == null ) || ( countryDto == null ) ) {
            return null;
        }

        country.setName( countryDto.getName() );
        country.setArchived( countryDto.getArchived() );

        return country;
    }

    public static CountryDto transform( Country country ) {

        if( country == null ) {
            return null;
        }

        CountryDto countryDto = new CountryDto();

        countryDto.setId( country.getId() );
        countryDto.setName( country.getName() );
        countryDto.setArchived( country.getArchived() );

        return countryDto;
    }
}
