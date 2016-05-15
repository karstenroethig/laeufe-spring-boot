package karstenroethig.laeufe.util;

public enum MessageKeyEnum {
	
	APPLICATION_VERSION( "application.version" ),

    /* ********************
    * Organizer.
    * ********************
    */
    /** "Organizer > Hinzufügen": Eingaben nicht valide. */
    ORGANIZER_SAVE_INVALID( "organizer.save.invalid" ),

    /** "Organizer > Hinzufügen": Erfolgreich gespeichert. */
    ORGANIZER_SAVE_SUCCESS( "organizer.save.success" ),

    /** "Organizer > Hinzufügen": Unerwarteter Fehler ist aufgetreten. */
    ORGANIZER_SAVE_ERROR( "organizer.save.error" ),

    /** "Organizer > Bearbeiten": Eingaben nicht vailde. */
    ORGANIZER_UPDATE_INVALID( "organizer.update.invalid" ),

    /** "Organizer > Bearbeiten": Erfolgreich gespeichert. */
    ORGANIZER_UPDATE_SUCCESS( "organizer.update.success" ),

    /** "Organizer > Bearbeiten": Unerwarteter Fehler ist aufgetreten. */
    ORGANIZER_UPDATE_ERROR( "organizer.update.error" ),

    /** "Organizer > Löschen": Erfolgreich gelöscht. */
    ORGANIZER_DELETE_SUCCESS( "organizer.delete.success" ),

    /** "Organizer > Löschen": Unerwarteter Fehler ist aufgetreten. */
    ORGANIZER_DELETE_ERROR( "organizer.delete.error" ),

    /* ********************
    * Event.
    * ********************
    */
    /** "Event > Hinzufügen": Eingaben nicht valide. */
    EVENT_SAVE_INVALID( "event.save.invalid" ),

    /** "Event > Hinzufügen": Erfolgreich gespeichert. */
    EVENT_SAVE_SUCCESS( "event.save.success" ),

    /** "Event > Hinzufügen": Unerwarteter Fehler ist aufgetreten. */
    EVENT_SAVE_ERROR( "event.save.error" ),

    /** "Event > Bearbeiten": Eingaben nicht vailde. */
    EVENT_UPDATE_INVALID( "event.update.invalid" ),

    /** "Event > Bearbeiten": Erfolgreich gespeichert. */
    EVENT_UPDATE_SUCCESS( "event.update.success" ),

    /** "Event > Bearbeiten": Unerwarteter Fehler ist aufgetreten. */
    EVENT_UPDATE_ERROR( "event.update.error" ),

    /** "Event > Löschen": Erfolgreich gelöscht. */
    EVENT_DELETE_SUCCESS( "event.delete.success" ),

    /** "Event > Löschen": Unerwarteter Fehler ist aufgetreten. */
    EVENT_DELETE_ERROR( "event.delete.error" );

    private String key;

    private MessageKeyEnum( String key ) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
