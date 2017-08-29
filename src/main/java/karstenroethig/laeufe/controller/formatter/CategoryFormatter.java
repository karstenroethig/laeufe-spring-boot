package karstenroethig.laeufe.controller.formatter;

import java.text.ParseException;
import java.util.Locale;

import karstenroethig.laeufe.dto.CategoryDto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class CategoryFormatter implements Formatter<CategoryDto>
{
	@Override
	public String print( CategoryDto category, Locale locale )
	{
		if ( ( category == null ) || ( category.getId() == null ) )
		{
			return StringUtils.EMPTY;
		}

		return category.getId().toString();
	}

	@Override
	public CategoryDto parse( String id, Locale locale ) throws ParseException
	{
		if ( StringUtils.isBlank( id ) && ( StringUtils.isNumeric( id ) == false ) )
		{
			return null;
		}

		CategoryDto category = new CategoryDto();

		category.setId( Long.parseLong( id ) );

		return category;
	}
}
