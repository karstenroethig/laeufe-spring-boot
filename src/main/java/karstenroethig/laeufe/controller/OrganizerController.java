package karstenroethig.laeufe.controller;

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

import karstenroethig.laeufe.controller.exceptions.NotFoundException;
import karstenroethig.laeufe.controller.util.UrlMappings;
import karstenroethig.laeufe.controller.util.ViewEnum;
import karstenroethig.laeufe.dto.OrganizerDto;
import karstenroethig.laeufe.service.OrganizerService;
import karstenroethig.laeufe.service.exceptions.OrganizerAlreadyExistsException;
import karstenroethig.laeufe.util.MessageKeyEnum;
import karstenroethig.laeufe.util.Messages;


@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_ORGANIZER )
public class OrganizerController {

    @Autowired
    OrganizerService organizerService;

    @RequestMapping(
        value = UrlMappings.ACTION_LIST,
        method = RequestMethod.GET
    )
    public String list( Model model ) {

        model.addAttribute( "allOrganizers", organizerService.getAllOrganizers() );

        return ViewEnum.ORGANIZER_LIST.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_CREATE,
        method = RequestMethod.GET
    )
    public String create( Model model ) {

        model.addAttribute( "organizer", organizerService.newOrganizer() );

        return ViewEnum.ORGANIZER_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_EDIT,
        method = RequestMethod.GET
    )
    public String edit( @PathVariable( "id" ) Long organizerId, Model model ) {

        OrganizerDto organizer = organizerService.findOrganizer( organizerId );

        if( organizer == null ) {
            throw new NotFoundException( String.valueOf( organizerId ) );
        }

        model.addAttribute( "organizer", organizer );

        return ViewEnum.ORGANIZER_EDIT.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_DELETE,
        method = RequestMethod.GET
    )
    public String delete( @PathVariable( "id" ) Long organizerId, final RedirectAttributes redirectAttributes,
        Model model ) {

        OrganizerDto organizer = organizerService.findOrganizer( organizerId );

        if( organizer == null ) {
            throw new NotFoundException( String.valueOf( organizerId ) );
        }

        if( organizerService.deleteOrganizer( organizerId ) ) {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithSuccess( MessageKeyEnum.ORGANIZER_DELETE_SUCCESS, organizer.getName() ) );
        } else {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.ORGANIZER_DELETE_ERROR, organizer.getName() ) );
        }

        return UrlMappings.redirect( UrlMappings.CONTROLLER_ORGANIZER, UrlMappings.ACTION_LIST );
    }

    @RequestMapping(
        value = UrlMappings.ACTION_SAVE,
        method = RequestMethod.POST
    )
    public String save( @ModelAttribute( "organizer" ) @Valid OrganizerDto organizer, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.ORGANIZER_SAVE_INVALID ) );

            return ViewEnum.ORGANIZER_CREATE.getViewName();
        }

        try {

            if( organizerService.saveOrganizer( organizer ) != null ) {
                redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                    Messages.createWithSuccess( MessageKeyEnum.ORGANIZER_SAVE_SUCCESS, organizer.getName() ) );

                return UrlMappings.redirect( UrlMappings.CONTROLLER_ORGANIZER, UrlMappings.ACTION_LIST );
            }
        } catch( OrganizerAlreadyExistsException ex ) {

            bindingResult.rejectValue( "name", "organizer.error.exists" );

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.ORGANIZER_SAVE_INVALID ) );

            return ViewEnum.ORGANIZER_CREATE.getViewName();
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.ORGANIZER_SAVE_ERROR ) );

        return ViewEnum.ORGANIZER_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_UPDATE,
        method = RequestMethod.POST
    )
    public String update( @ModelAttribute( "organizer" ) @Valid OrganizerDto organizer, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.ORGANIZER_UPDATE_INVALID ) );

            return ViewEnum.ORGANIZER_EDIT.getViewName();
        }

        try {

            if( organizerService.editOrganizer( organizer ) != null ) {
                redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                    Messages.createWithSuccess( MessageKeyEnum.ORGANIZER_UPDATE_SUCCESS, organizer.getName() ) );

                return UrlMappings.redirect( UrlMappings.CONTROLLER_ORGANIZER, UrlMappings.ACTION_LIST );
            }
        } catch( OrganizerAlreadyExistsException ex ) {

            bindingResult.rejectValue( "name", "organizer.error.exists" );

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.ORGANIZER_UPDATE_INVALID ) );

            return ViewEnum.ORGANIZER_EDIT.getViewName();
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.ORGANIZER_UPDATE_ERROR ) );

        return ViewEnum.ORGANIZER_EDIT.getViewName();
    }

    @ExceptionHandler( NotFoundException.class )
    void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(),
            String.format( "Organizer %s does not exist.", ex.getMessage() ) );
    }
}
