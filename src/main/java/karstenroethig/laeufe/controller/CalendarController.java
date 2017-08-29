package karstenroethig.laeufe.controller;

import karstenroethig.laeufe.controller.util.UrlMappings;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ComponentScan
@Controller
public class CalendarController
{
	@RequestMapping(
		value = UrlMappings.CALENDAR,
		method = RequestMethod.GET
	)
	public String calendar( Model model )
	{
		return "views/calendar";
	}
}