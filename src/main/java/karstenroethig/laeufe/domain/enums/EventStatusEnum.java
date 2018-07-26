package karstenroethig.laeufe.domain.enums;

import lombok.Getter;

@Getter
public enum EventStatusEnum
{
	/*
	 * event status depends on race status
	 * - planed: default and if there are no races
	 * - registered: if there is at least one race with status registered
	 * - completed: if there is at least one race with status completed
	 * - failed: if races have only status failed or status dnp
	 * - dnp: only if all races have status dnp
	 * - canceled: only if all races have status canceled
	 */

	PLANED( 0, "fa fa-calendar", "label label-default" ),
	REGISTERED( 1, "fa fa-calendar-check-o", "label label-success" ),
	COMPLETED( 2, "pficon pficon-ok", "label label-success" ),
	FAILED( 3, "pficon pficon-error-circle-o", "label label-danger" ),
	DNP( 4, "pficon pficon-delete", "label label-danger" ),
	CANCELED( 5, "fa fa-ban", "label label-danger" );

	private int key;
	private String icon;
	private String label;

	private EventStatusEnum( int key, String icon, String label )
	{
		this.key = key;
		this.icon = icon;
		this.label = label;
	}

	public static EventStatusEnum getStatusForKey( Integer key )
	{
		if ( key == null )
		{
			return null;
		}

		for ( EventStatusEnum status : values() )
		{
			if ( key.equals( status.getKey() ) )
			{
				return status;
			}
		}

		return null;
	}
}
