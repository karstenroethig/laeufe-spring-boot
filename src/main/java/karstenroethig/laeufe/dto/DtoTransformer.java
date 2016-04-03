package karstenroethig.laeufe.dto;


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
}
