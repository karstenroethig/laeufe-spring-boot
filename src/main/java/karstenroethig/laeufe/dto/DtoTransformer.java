package karstenroethig.laeufe.dto;

import org.apache.commons.lang3.StringUtils;

import karstenroethig.laeufe.domain.Category;
import karstenroethig.laeufe.domain.Country;
import karstenroethig.laeufe.domain.Organizer;
import karstenroethig.laeufe.dto.api.CountryApiDto;

public class DtoTransformer
{
	private DtoTransformer() {}

	/*
	 * =========
	 * Organizer
	 * =========
	 */

	public static Organizer merge( Organizer organizer, OrganizerDto organizerDto )
	{
		if ( ( organizer == null ) || ( organizerDto == null ) )
		{
			return null;
		}

		organizer.setName( organizerDto.getName() );
		organizer.setDescription( StringUtils.trimToNull( organizerDto.getDescription() ) );
		organizer.setLogoFile( StringUtils.trimToNull( organizerDto.getLogoFile() ) );
		organizer.setArchived( organizerDto.getArchived() );

		return organizer;
	}

	public static OrganizerDto transform( Organizer organizer )
	{
		if ( organizer == null )
		{
			return null;
		}

		OrganizerDto organizerDto = new OrganizerDto();

		organizerDto.setId( organizer.getId() );
		organizerDto.setName( organizer.getName() );
		organizerDto.setDescription( StringUtils.trimToNull( organizer.getDescription() ) );
		organizerDto.setLogoFile( StringUtils.trimToNull( organizer.getLogoFile() ) );
		organizerDto.setArchived( organizer.getArchived() );

		return organizerDto;
	}

	/*
	 * =======
	 * Country
	 * =======
	 */

	public static Country merge( Country country, CountryDto countryDto )
	{
		if ( ( country == null ) || ( countryDto == null ) )
		{
			return null;
		}

		country.setName( countryDto.getName() );
		country.setCode( countryDto.getCode() );
		country.setArchived( countryDto.getArchived() );

		return country;
	}

	public static CountryDto transform( Country country )
	{
		if ( country == null )
		{
			return null;
		}

		CountryDto countryDto = new CountryDto();

		countryDto.setId( country.getId() );
		countryDto.setName( country.getName() );
		countryDto.setCode( country.getCode() );
		countryDto.setArchived( country.getArchived() );

		return countryDto;
	}

	public static CountryApiDto transformApi( Country country )
	{
		if ( country == null )
		{
			return null;
		}

		CountryApiDto countryDto = new CountryApiDto();

		countryDto.setCode( country.getCode() );

		return countryDto;
	}

	/*
	 * ========
	 * Category
	 * ========
	 */

	public static Category merge( Category category, CategoryDto categoryDto )
	{
		if ( ( category == null ) || ( categoryDto == null ) )
		{
			return null;
		}

		category.setName( categoryDto.getName() );
		category.setDescription( categoryDto.getDescription() );
		category.setArchived( categoryDto.getArchived() );

		return category;
	}

	public static CategoryDto transform( Category category )
	{
		if ( category == null )
		{
			return null;
		}

		CategoryDto categoryDto = new CategoryDto();

		categoryDto.setId( category.getId() );
		categoryDto.setName( category.getName() );
		categoryDto.setDescription( category.getDescription() );
		categoryDto.setArchived( category.getArchived() );

		return categoryDto;
	}
}
