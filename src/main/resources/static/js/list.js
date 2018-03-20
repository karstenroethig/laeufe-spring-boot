/**
 * Executes some initialization operations when the DOM is fully loaded.
 */
$( document ).ready( function()
{
	// initialize the accordion element: hide the selected parameters if the accordion is open
	$( '#search_accordion' ).on( 'show.bs.collapse', function( event ) {
		$( '#searchParametersHint' ).text( '' );
	});

	// initialize the accordion element: show the selected parameters if the accordion is closed
	$( '#search_accordion' ).on( 'hide.bs.collapse', function( event ) {
		updateSearchParameterHint();
	});

	// initialize the accordion element: it's closed by default, so the selected parameters have to be displayed
	updateSearchParameterHint();
});

/**
 * Collects the selected parameters of the search form, generates a text of it and displays the text in the hint section.
 * The hint section is specified by an element with the id "searchParametersHint".
 */
function updateSearchParameterHint()
{
	var organizerNames = getSelectedOptionsFromSelectAsText('search_param_organizer');
	var name = $('#search_param_name').val();
	var periodFrom = $('#search_param_period_from').val();
	var periodTo = $('#search_param_period_to').val();

	var hint = '';
	
	if (organizerNames != null && organizerNames !== '')
	{
		hint += 'Veranstalter: ';
		hint += organizerNames;
	}
	
	if (name != null && name !== '')
	{
		if (hint.length > 0)
			hint += '; ';

		hint += 'Name: ';
		hint += name;
	}
	
	if ((periodFrom != null && periodFrom !== '') || (periodTo != null && periodTo !== ''))
	{
		if (hint.length > 0)
			hint += '; ';

		hint += 'Zeitraum: ';
		
		if (periodFrom != null && periodFrom !== '')
		{
			hint += periodFrom;
		}
		else
		{
			hint += '∞';
		}
		
		hint += '-';

		if( periodTo != null && periodTo !== '')
		{
			hint += periodTo;
		}
		else
		{
			hint += '∞';
		}
	}

	$('#searchParametersHint').text(hint);
}

/**
 * Generates a text for the selected option of a select field.
 * 
 * @param id
 *            the id of the select field
 * @returns a {String} with the generated text or {null} if there is no option selected
 */
function getSelectedOptionsFromSelectAsText(id)
{
	var text = null;
	$('#' + id + ' option:selected').each(function()
	{
		if (text == null)
		{
			text = '';
		}
		else
		{
			text += ', ';
		}
		text += $(this).text();
	});
	
	return text;
}