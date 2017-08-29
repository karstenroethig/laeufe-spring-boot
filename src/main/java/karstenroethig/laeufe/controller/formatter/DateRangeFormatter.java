package karstenroethig.laeufe.controller.formatter;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import karstenroethig.laeufe.dto.DateRange;

public class DateRangeFormatter
{
	private static Map<String, DateTimeFormatter> dateFormatterCache = new HashMap<>();

	public static String format( DateRange dateRange, String dateFormatPattern )
	{
		if ( dateRange == null )
		{
			return StringUtils.EMPTY;
		}

		DateTimeFormatter dateFormatter;

		if ( dateFormatterCache.containsKey( dateFormatPattern ) )
		{
			dateFormatter = dateFormatterCache.get( dateFormatPattern );
		}
		else
		{
			dateFormatter = DateTimeFormatter.ofPattern( dateFormatPattern );
			dateFormatterCache.put( dateFormatPattern, dateFormatter );
		}

		StringBuffer result = new StringBuffer();

		if ( dateRange.getStartDate() == null )
		{
			result.append( "?" );
		}
		else
		{
			result.append( dateRange.getStartDate().format( dateFormatter ) );
		}

		if ( dateRange.getEndDate() != null )
		{
			result.append( "-" );
			result.append( dateRange.getEndDate().format( dateFormatter ) );
		}

		return result.toString();
	}
}
