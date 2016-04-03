package karstenroethig.laeufe.controller.util;

public class UrlMappings {

    private static final String REDIRECT_PREFIX = "redirect:";

    public static final String HOME = "/";

    public static final String CONTROLLER_ORGANIZER = "/organizer";
    public static final String CONTROLLER_ADMIN = "/admin";

    public static final String ACTION_LIST = "/list";
    public static final String ACTION_SHOW = "/show/{id}";
    public static final String ACTION_CREATE = "/create";
    public static final String ACTION_EDIT = "/edit/{id}";
    public static final String ACTION_DELETE = "/delete/{id}";
    public static final String ACTION_SAVE = "/save";
    public static final String ACTION_UPDATE = "/update";

    private UrlMappings() {
    }

    public static String redirect( String controllerPath, String actionPath ) {
        return REDIRECT_PREFIX + controllerPath + actionPath;
    }
}
