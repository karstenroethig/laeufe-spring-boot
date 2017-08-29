package karstenroethig.laeufe.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Messages
{
	public static final String ATTRIBUTE_NAME = "messages";

	private List<Message> errors = null;
	private List<Message> warnings = null;
	private List<Message> infos = null;
	private List<Message> successes = null;

	public static Messages createWithError( MessageKeyEnum messageKeyEnum, Object... params )
	{
		Messages messages = new Messages();

		messages.addError( messageKeyEnum, params );

		return messages;
	}

	public static Messages createWithWarning( MessageKeyEnum messageKeyEnum, Object... params )
	{
		Messages messages = new Messages();

		messages.addWarning( messageKeyEnum, params );

		return messages;
	}

	public static Messages createWithInfo( MessageKeyEnum messageKeyEnum, Object... params )
	{
		Messages messages = new Messages();

		messages.addInfo( messageKeyEnum, params );

		return messages;
	}

	public static Messages createWithSuccess( MessageKeyEnum messageKeyEnum, Object... params )
	{
		Messages messages = new Messages();

		messages.addSuccess( messageKeyEnum, params );

		return messages;
	}

	public void addError( MessageKeyEnum messageKeyEnum, Object... params )
	{
		if ( errors == null )
		{
			errors = new ArrayList<Message>();
		}

		errors.add( new Message( messageKeyEnum.getKey(), params ) );
	}

	public void addWarning( MessageKeyEnum messageKeyEnum, Object... params )
	{
		if ( warnings == null )
		{
			warnings = new ArrayList<Message>();
		}

		warnings.add( new Message( messageKeyEnum.getKey(), params ) );
	}

	public void addInfo( MessageKeyEnum messageKeyEnum, Object... params )
	{
		if ( infos == null )
		{
			infos = new ArrayList<Message>();
		}

		infos.add( new Message( messageKeyEnum.getKey(), params ) );
	}

	public void addSuccess( MessageKeyEnum messageKeyEnum, Object... params )
	{
		if ( successes == null )
		{
			successes = new ArrayList<Message>();
		}

		successes.add( new Message( messageKeyEnum.getKey(), params ) );
	}

	public boolean hasErrors()
	{
		return ( errors != null ) && ( errors.isEmpty() == false );
	}

	public boolean hasWarnings()
	{
		return ( warnings != null ) && ( warnings.isEmpty() == false );
	}

	public boolean hasInfos()
	{
		return ( infos != null ) && ( infos.isEmpty() == false );
	}

	public boolean hasSuccesses()
	{
		return ( successes != null ) && ( successes.isEmpty() == false );
	}
}
