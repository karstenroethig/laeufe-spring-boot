
$( document ).ready( function() {

	$( function () {
		
		// initialize bootstrap tooltips
		$('[data-toggle="tooltip"]').tooltip();
		
		// initialize bootstrap switch
		$('.bootstrap-switch').bootstrapSwitch();
		
		// initialize bootstrap datepicker
		$( '.input-group.date' ).datepicker({
			format: "dd.mm.yyyy",
			weekStart: 1,
			clearBtn: true,
			todayBtn: "linked",
			todayHighlight: true,
			orientation: "bottom left",
			language: "de",
			daysOfWeekHighlighted: "0,6",
			calendarWeeks: true,
			autoclose: true
		});
	});
	
	// delete modals: transfer the id to the modal form
	$( '#deleteModal' ).on( 'show.bs.modal', function( event ) {
		var button = $( event.relatedTarget ); // Button that triggered the modal
		var id = button.data( 'id' ); // Extract info from data-* attributes

		// Update the modal's content.
		var modal = $( this );
		var link = modal.find( '.btn-danger' );
		var template = link.data( 'href-template' );
		link.attr( 'href', template.replace( '{id}', id ) );
	});

});