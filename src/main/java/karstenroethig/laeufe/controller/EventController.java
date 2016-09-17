package karstenroethig.laeufe.controller;

import karstenroethig.laeufe.controller.exceptions.NotFoundException;
import karstenroethig.laeufe.controller.util.UrlMappings;
import karstenroethig.laeufe.controller.util.ViewEnum;

import karstenroethig.laeufe.dto.EventDto;

import karstenroethig.laeufe.service.CountryService;
import karstenroethig.laeufe.service.EventService;
import karstenroethig.laeufe.service.OrganizerService;

import karstenroethig.laeufe.util.MessageKeyEnum;
import karstenroethig.laeufe.util.Messages;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;


@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_EVENT )
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    OrganizerService organizerService;

    @Autowired
    CountryService countryService;

    @RequestMapping(
        value = UrlMappings.ACTION_LIST,
        method = RequestMethod.GET
    )
    public String list( Model model ) {

        model.addAttribute( "allEvents", eventService.getAllEvents() );

        return ViewEnum.EVENT_LIST.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_CREATE,
        method = RequestMethod.GET
    )
    public String create( Model model ) {

        model.addAttribute( "event", eventService.newEvent() );
        model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
        model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );

        return ViewEnum.EVENT_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_SHOW,
        method = RequestMethod.GET
    )
    public String show( @PathVariable( "id" ) Long eventId, Model model ) {

        EventDto event = eventService.findEvent( eventId );

        if( event == null ) {
            throw new NotFoundException( String.valueOf( eventId ) );
        }

        model.addAttribute( "event", event );

        return ViewEnum.EVENT_SHOW.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_EDIT,
        method = RequestMethod.GET
    )
    public String edit( @PathVariable( "id" ) Long eventId, Model model ) {

        EventDto event = eventService.findEvent( eventId );

        if( event == null ) {
            throw new NotFoundException( String.valueOf( eventId ) );
        }

        model.addAttribute( "event", event );
        model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
        model.addAttribute( "allArchivedOrganizers", organizerService.getAllArchivedOrganizers() );
        model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );
        model.addAttribute( "allArchivedCountries", countryService.getAllArchivedCountries() );

        return ViewEnum.EVENT_EDIT.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_DELETE,
        method = RequestMethod.GET
    )
    public String delete( @PathVariable( "id" ) Long eventId, final RedirectAttributes redirectAttributes,
        Model model ) {

        EventDto event = eventService.findEvent( eventId );

        if( event == null ) {
            throw new NotFoundException( String.valueOf( eventId ) );
        }

        if( eventService.deleteEvent( eventId ) ) {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithSuccess( MessageKeyEnum.EVENT_DELETE_SUCCESS, event.getName() ) );
        } else {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.EVENT_DELETE_ERROR, event.getName() ) );
        }

        return UrlMappings.redirect( UrlMappings.CONTROLLER_EVENT, UrlMappings.ACTION_LIST );
    }

    @RequestMapping(
        value = UrlMappings.ACTION_SAVE,
        method = RequestMethod.POST
    )
    public String save( @ModelAttribute( "event" ) @Valid EventDto event, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.EVENT_SAVE_INVALID ) );

            model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
            model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );

            return ViewEnum.EVENT_CREATE.getViewName();
        }

        if( eventService.saveEvent( event ) != null ) {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithSuccess( MessageKeyEnum.EVENT_SAVE_SUCCESS, event.getName() ) );

            return UrlMappings.redirect( UrlMappings.CONTROLLER_EVENT, UrlMappings.ACTION_LIST );
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.EVENT_SAVE_ERROR ) );

        model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
        model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );

        return ViewEnum.EVENT_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_UPDATE,
        method = RequestMethod.POST
    )
    public String update( @ModelAttribute( "event" ) @Valid EventDto event, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.EVENT_UPDATE_INVALID ) );

            model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
            model.addAttribute( "allArchivedOrganizers", organizerService.getAllArchivedOrganizers() );
            model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );
            model.addAttribute( "allArchivedCountries", countryService.getAllArchivedCountries() );

            return ViewEnum.EVENT_EDIT.getViewName();
        }

        if( eventService.editEvent( event ) != null ) {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithSuccess( MessageKeyEnum.EVENT_UPDATE_SUCCESS, event.getName() ) );

            return UrlMappings.redirect( UrlMappings.CONTROLLER_EVENT, UrlMappings.ACTION_LIST );
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.EVENT_UPDATE_ERROR ) );

        model.addAttribute( "allUnarchivedOrganizers", organizerService.getAllUnarchivedOrganizers() );
        model.addAttribute( "allArchivedOrganizers", organizerService.getAllArchivedOrganizers() );
        model.addAttribute( "allUnarchivedCountries", countryService.getAllUnarchivedCountries() );
        model.addAttribute( "allArchivedCountries", countryService.getAllArchivedCountries() );

        return ViewEnum.EVENT_EDIT.getViewName();
    }

    @ExceptionHandler( NotFoundException.class )
    void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(),
            String.format( "Event %s does not exist.", ex.getMessage() ) );
    }
}
