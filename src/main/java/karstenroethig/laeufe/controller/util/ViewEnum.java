package karstenroethig.laeufe.controller.util;

import org.apache.commons.lang3.StringUtils;

public enum ViewEnum
{
	ORGANIZER_CREATE( ControllerEnum.organizer, ActionEnum.create ),
	ORGANIZER_EDIT( ControllerEnum.organizer, ActionEnum.edit ),
	ORGANIZER_LIST( ControllerEnum.organizer, ActionEnum.list ),

	COUNTRY_CREATE( ControllerEnum.country, ActionEnum.create ),
	COUNTRY_EDIT( ControllerEnum.country, ActionEnum.edit ),
	COUNTRY_LIST( ControllerEnum.country, ActionEnum.list ),

	CATEGORY_CREATE( ControllerEnum.category, ActionEnum.create ),
	CATEGORY_EDIT( ControllerEnum.category, ActionEnum.edit ),
	CATEGORY_LIST( ControllerEnum.category, ActionEnum.list ),

	EVENT_CREATE( ControllerEnum.event, ActionEnum.create ),
	EVENT_SHOW( ControllerEnum.event, ActionEnum.show ),
	EVENT_EDIT( ControllerEnum.event, ActionEnum.edit ),
	EVENT_LIST( ControllerEnum.event, ActionEnum.list ),

	ADMIN_SERVER_INFO( ControllerEnum.admin, "server-info" ),

	USER_ACCOUNT_INFO( ControllerEnum.user, "account-info" );

	private static final String VIEW_SUBDIRECTORY = "views";

	private String viewName = StringUtils.EMPTY;

	private enum ControllerEnum
	{
		admin, category, country, event, organizer, user;
	}

	private enum ActionEnum
	{
		create, edit, list, show;
	}

	private ViewEnum( ControllerEnum controller, ActionEnum action )
	{
		this( controller, action.name() );
	}

	private ViewEnum( ControllerEnum controller, String action )
	{
		viewName += VIEW_SUBDIRECTORY;

		if ( StringUtils.isNoneBlank( viewName ) )
		{
			viewName += "/";
		}

		viewName += controller.name();
		viewName += "/";
		viewName += action;
	}

	public String getViewName()
	{
		return viewName;
	}
}
