package karstenroethig.laeufe.domain.enums;

public enum EventStatusEnum {

	/*
	 * event status depends on race status
	 * - planed: default and if there are no races
	 * - registered: if there is at least one race with status registered
	 * - completed: if there is at least one race with status completed
	 * - failed: only if all races have status failed
	 */
	
	PLANED( 0, "fa fa-calendar" ),
	REGISTERED( 1, "fa fa-calendar-check-o" ),
	COMPLETED( 2, "pficon pficon-ok" ),
	FAILED( 3, "pficon pficon-error-circle-o" );
	
	private int key;
	private String icon;
	
	private EventStatusEnum( int key, String icon ) {
		this.key = key;
		this.icon = icon;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public static EventStatusEnum getStatusForKey( Integer key ) {
		
		if( key == null ) {
			return null;
		}
		
		for( EventStatusEnum status : values() ) {
			
			if( key.equals( status.getKey() ) ) {
				return status;
			}
		}
		
		return null;
	}
}
