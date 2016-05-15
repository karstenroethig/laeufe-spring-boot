package karstenroethig.laeufe.controller.formatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import karstenroethig.laeufe.dto.DateRange;

import org.apache.commons.lang3.StringUtils;


public class DateRangeFormatter {
	
	private static Map<String, DateFormat> dateFormatCache = new HashMap<String, DateFormat>();
	
	public static String format( DateRange dateRange, String dateFormatPattern ) {
		
		if( dateRange == null ) {
			return StringUtils.EMPTY;
		}
		
		DateFormat dateFormat;
		
		if( dateFormatCache.containsKey( dateFormatPattern ) ) {
			dateFormat = dateFormatCache.get( dateFormatPattern );
		} else {
			dateFormat = new SimpleDateFormat( dateFormatPattern );
			dateFormatCache.put( dateFormatPattern, dateFormat );
		}
		
		StringBuffer result = new StringBuffer();
		
		if( dateRange.getStartDate() == null ) {
			result.append( "?" );
		} else {
			result.append( dateFormat.format( dateRange.getStartDate() ) );
		}

		if( dateRange.getEndDate() != null ) {
			result.append( "-" );
			result.append( dateFormat.format( dateRange.getEndDate() ) );
		}
		
		return result.toString();
	}
}
