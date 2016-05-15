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
import karstenroethig.laeufe.dto.CountryDto;
import karstenroethig.laeufe.service.CountryService;
import karstenroethig.laeufe.service.exceptions.CountryAlreadyExistsException;
import karstenroethig.laeufe.util.MessageKeyEnum;
import karstenroethig.laeufe.util.Messages;


@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_COUNTRY )
public class CountryController {

    @Autowired
    CountryService countryService;

    @RequestMapping(
        value = UrlMappings.ACTION_LIST,
        method = RequestMethod.GET
    )
    public String list( Model model ) {

        model.addAttribute( "allCountries", countryService.getAllCountries() );

        return ViewEnum.COUNTRY_LIST.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_CREATE,
        method = RequestMethod.GET
    )
    public String create( Model model ) {

        model.addAttribute( "country", countryService.newCountry() );

        return ViewEnum.COUNTRY_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_EDIT,
        method = RequestMethod.GET
    )
    public String edit( @PathVariable( "id" ) Long countryId, Model model ) {

        CountryDto country = countryService.findCountry( countryId );

        if( country == null ) {
            throw new NotFoundException( String.valueOf( countryId ) );
        }

        model.addAttribute( "country", country );

        return ViewEnum.COUNTRY_EDIT.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_DELETE,
        method = RequestMethod.GET
    )
    public String delete( @PathVariable( "id" ) Long countryId, final RedirectAttributes redirectAttributes,
        Model model ) {

        CountryDto country = countryService.findCountry( countryId );

        if( country == null ) {
            throw new NotFoundException( String.valueOf( countryId ) );
        }

        if( countryService.deleteCountry( countryId ) ) {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithSuccess( MessageKeyEnum.COUNTRY_DELETE_SUCCESS, country.getName() ) );
        } else {
            redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.COUNTRY_DELETE_ERROR, country.getName() ) );
        }

        return UrlMappings.redirect( UrlMappings.CONTROLLER_COUNTRY, UrlMappings.ACTION_LIST );
    }

    @RequestMapping(
        value = UrlMappings.ACTION_SAVE,
        method = RequestMethod.POST
    )
    public String save( @ModelAttribute( "country" ) @Valid CountryDto country, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.COUNTRY_SAVE_INVALID ) );

            return ViewEnum.COUNTRY_CREATE.getViewName();
        }

        try {

            if( countryService.saveCountry( country ) != null ) {
                redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                    Messages.createWithSuccess( MessageKeyEnum.COUNTRY_SAVE_SUCCESS, country.getName() ) );

                return UrlMappings.redirect( UrlMappings.CONTROLLER_COUNTRY, UrlMappings.ACTION_LIST );
            }
        } catch( CountryAlreadyExistsException ex ) {

            bindingResult.rejectValue( "name", "country.error.exists" );

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.COUNTRY_SAVE_INVALID ) );

            return ViewEnum.COUNTRY_CREATE.getViewName();
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COUNTRY_SAVE_ERROR ) );

        return ViewEnum.COUNTRY_CREATE.getViewName();
    }

    @RequestMapping(
        value = UrlMappings.ACTION_UPDATE,
        method = RequestMethod.POST
    )
    public String update( @ModelAttribute( "country" ) @Valid CountryDto country, BindingResult bindingResult,
    		final RedirectAttributes redirectAttributes, Model model ) {

        if( bindingResult.hasErrors() ) {

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.COUNTRY_UPDATE_INVALID ) );

            return ViewEnum.COUNTRY_EDIT.getViewName();
        }

        try {

            if( countryService.editCountry( country ) != null ) {
                redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
                    Messages.createWithSuccess( MessageKeyEnum.COUNTRY_UPDATE_SUCCESS, country.getName() ) );

                return UrlMappings.redirect( UrlMappings.CONTROLLER_COUNTRY, UrlMappings.ACTION_LIST );
            }
        } catch( CountryAlreadyExistsException ex ) {

            bindingResult.rejectValue( "name", "country.error.exists" );

            model.addAttribute( Messages.ATTRIBUTE_NAME,
                Messages.createWithError( MessageKeyEnum.COUNTRY_UPDATE_INVALID ) );

            return ViewEnum.COUNTRY_EDIT.getViewName();
        }

        model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.COUNTRY_UPDATE_ERROR ) );

        return ViewEnum.COUNTRY_EDIT.getViewName();
    }

    @ExceptionHandler( NotFoundException.class )
    void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(),
            String.format( "Country %s does not exist.", ex.getMessage() ) );
    }
}
