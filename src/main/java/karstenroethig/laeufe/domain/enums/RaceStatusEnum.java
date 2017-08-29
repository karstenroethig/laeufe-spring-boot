package karstenroethig.laeufe.domain.enums;

import lombok.Getter;

@Getter
public enum RaceStatusEnum
{
	REGISTERED( 1, "fa fa-calendar-check-o" ),
	COMPLETED( 2, "pficon pficon-ok" ),
	FAILED( 3, "pficon pficon-error-circle-o" );

	private int key;
	private String icon;

	private RaceStatusEnum( int key, String icon )
	{
		this.key = key;
		this.icon = icon;
	}

	public static RaceStatusEnum getStatusForKey( Integer key )
	{
		if ( key == null )
		{
			return null;
		}

		for ( RaceStatusEnum status : values() )
		{
			if ( key.equals( status.getKey() ) )
			{
				return status;
			}
		}

		return null;
	}
}
