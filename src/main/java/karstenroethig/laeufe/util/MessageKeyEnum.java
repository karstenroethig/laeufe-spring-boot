package karstenroethig.laeufe.util;

public enum MessageKeyEnum {

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
    ORGANIZER_DELETE_ERROR( "organizer.delete.error" );

    private String key;

    private MessageKeyEnum( String key ) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
