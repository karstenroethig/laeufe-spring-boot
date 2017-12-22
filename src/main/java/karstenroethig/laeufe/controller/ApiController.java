package karstenroethig.laeufe.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import karstenroethig.laeufe.controller.util.UrlMappings;
import karstenroethig.laeufe.dto.api.CountryApiDto;
import karstenroethig.laeufe.dto.api.LocationApiDto;
import karstenroethig.laeufe.service.EventService;

@RestController
@RequestMapping( UrlMappings.CONTROLLER_API + UrlMappings.CONTROLLER_API_VERSION_1_0 )
public class ApiController {

	@Autowired
	EventService eventService;

	@RequestMapping(value = "/eventLocations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<LocationApiDto>> eventLocations()
	{
		Collection<LocationApiDto> locations = eventService.findEventLocations();

		return new ResponseEntity<Collection<LocationApiDto>>( locations, HttpStatus.OK );
	}

	@RequestMapping(value = "/eventLocationCountries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<CountryApiDto>> eventLocationCountries()
	{
		Collection<CountryApiDto> countries = eventService.findEventLocationCountries();

		return new ResponseEntity<Collection<CountryApiDto>>( countries, HttpStatus.OK );
	}
}
