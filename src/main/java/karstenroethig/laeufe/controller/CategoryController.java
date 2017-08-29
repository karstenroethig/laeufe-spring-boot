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
import karstenroethig.laeufe.dto.CategoryDto;
import karstenroethig.laeufe.service.CategoryService;
import karstenroethig.laeufe.service.exceptions.CategoryAlreadyExistsException;
import karstenroethig.laeufe.util.MessageKeyEnum;
import karstenroethig.laeufe.util.Messages;

@ComponentScan
@Controller
@RequestMapping( UrlMappings.CONTROLLER_CATEGORY )
public class CategoryController
{
	@Autowired
	CategoryService categoryService;

	@RequestMapping(
		value = UrlMappings.ACTION_LIST,
		method = RequestMethod.GET
	)
	public String list( Model model )
	{
		model.addAttribute( "allCategories", categoryService.getAllCategories() );

		return ViewEnum.CATEGORY_LIST.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_CREATE,
		method = RequestMethod.GET
	)
	public String create( Model model )
	{
		model.addAttribute( "category", categoryService.newCategory() );

		return ViewEnum.CATEGORY_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_EDIT,
		method = RequestMethod.GET
	)
	public String edit( @PathVariable( "id" ) Long categoryId, Model model )
	{
		CategoryDto category = categoryService.findCategory( categoryId );

		if ( category == null )
		{
			throw new NotFoundException( String.valueOf( categoryId ) );
		}

		model.addAttribute( "category", category );

		return ViewEnum.CATEGORY_EDIT.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_DELETE,
		method = RequestMethod.GET
	)
	public String delete( @PathVariable( "id" ) Long categoryId, final RedirectAttributes redirectAttributes, Model model )
	{
		CategoryDto category = categoryService.findCategory( categoryId );

		if ( category == null )
		{
			throw new NotFoundException( String.valueOf( categoryId ) );
		}

		if ( categoryService.deleteCategory( categoryId ) )
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithSuccess( MessageKeyEnum.CATEGORY_DELETE_SUCCESS, category.getName() ) );
		}
		else
		{
			redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
				Messages.createWithError( MessageKeyEnum.CATEGORY_DELETE_ERROR, category.getName() ) );
		}

		return UrlMappings.redirect( UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST );
	}

	@RequestMapping(
		value = UrlMappings.ACTION_SAVE,
		method = RequestMethod.POST
	)
	public String save( @ModelAttribute( "category" ) @Valid CategoryDto category, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_SAVE_INVALID ) );

			return ViewEnum.CATEGORY_CREATE.getViewName();
		}

		try
		{
			if ( categoryService.saveCategory( category ) != null )
			{
				redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess( MessageKeyEnum.CATEGORY_SAVE_SUCCESS, category.getName() ) );

				return UrlMappings.redirect( UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST );
			}
		}
		catch ( CategoryAlreadyExistsException ex )
		{
			bindingResult.rejectValue( "name", "category.error.exists" );

			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_SAVE_INVALID ) );

			return ViewEnum.CATEGORY_CREATE.getViewName();
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_SAVE_ERROR ) );

		return ViewEnum.CATEGORY_CREATE.getViewName();
	}

	@RequestMapping(
		value = UrlMappings.ACTION_UPDATE,
		method = RequestMethod.POST
	)
	public String update( @ModelAttribute( "category" ) @Valid CategoryDto category, BindingResult bindingResult,
		final RedirectAttributes redirectAttributes, Model model )
	{
		if ( bindingResult.hasErrors() )
		{
			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_UPDATE_INVALID ) );

			return ViewEnum.CATEGORY_EDIT.getViewName();
		}

		try
		{
			if ( categoryService.editCategory( category ) != null )
			{
				redirectAttributes.addFlashAttribute( Messages.ATTRIBUTE_NAME,
					Messages.createWithSuccess( MessageKeyEnum.CATEGORY_UPDATE_SUCCESS, category.getName() ) );

				return UrlMappings.redirect( UrlMappings.CONTROLLER_CATEGORY, UrlMappings.ACTION_LIST );
			}
		}
		catch ( CategoryAlreadyExistsException ex )
		{
			bindingResult.rejectValue( "name", "category.error.exists" );

			model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_UPDATE_INVALID ) );

			return ViewEnum.CATEGORY_EDIT.getViewName();
		}

		model.addAttribute( Messages.ATTRIBUTE_NAME, Messages.createWithError( MessageKeyEnum.CATEGORY_UPDATE_ERROR ) );

		return ViewEnum.CATEGORY_EDIT.getViewName();
	}

	@ExceptionHandler( NotFoundException.class )
	void handleNotFoundException( HttpServletResponse response, NotFoundException ex ) throws IOException {
		response.sendError( HttpStatus.NOT_FOUND.value(), String.format( "Category %s does not exist.", ex.getMessage() ) );
	}
}
