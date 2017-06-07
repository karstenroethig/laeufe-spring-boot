package karstenroethig.laeufe.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karstenroethig.laeufe.domain.Category;
import karstenroethig.laeufe.dto.CategoryDto;
import karstenroethig.laeufe.dto.DtoTransformer;
import karstenroethig.laeufe.repository.CategoryRepository;
import karstenroethig.laeufe.service.CategoryService;
import karstenroethig.laeufe.service.exceptions.CategoryAlreadyExistsException;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Override
    public CategoryDto newCategory() {

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setArchived( Boolean.FALSE );

        return categoryDto;
    }

    @Override
    public CategoryDto saveCategory( CategoryDto categoryDto ) throws CategoryAlreadyExistsException {

        List<Category> existingCategories = categoryRepository.findByNameIgnoreCase(
                StringUtils.trim( categoryDto.getName() ) );

        if( existingCategories != null && existingCategories.isEmpty() == false ) {
            throw new CategoryAlreadyExistsException();
        }

        Category category = new Category();

        category = DtoTransformer.merge( category, categoryDto );

        return DtoTransformer.transform( categoryRepository.save( category ) );
    }

    @Override
    public Boolean deleteCategory( Long categoryId ) {

        Category temp = categoryRepository.findOne( categoryId );

        if( temp != null ) {
            categoryRepository.delete( temp );

            return true;
        }

        return false;
    }

    @Override
    public CategoryDto editCategory( CategoryDto categoryDto ) throws CategoryAlreadyExistsException {

        List<Category> existingCategories = categoryRepository.findByNameIgnoreCase(
                StringUtils.trim( categoryDto.getName() ) );

        if( existingCategories != null && existingCategories.isEmpty() == false
                && existingCategories.get( 0 ).getId().equals( categoryDto.getId() ) == false ) {
            throw new CategoryAlreadyExistsException();
        }

        Category category = categoryRepository.findOne( categoryDto.getId() );

        category = DtoTransformer.merge( category, categoryDto );

        return DtoTransformer.transform( categoryRepository.save( category ) );
    }

    @Override
    public CategoryDto findCategory( Long categoryId ) {
        return DtoTransformer.transform( categoryRepository.findOne( categoryId ) );
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return transformCategories( categoryRepository.findAll() );
    }

    @Override
    public List<CategoryDto> getAllArchivedCategories() {
        return transformCategories( categoryRepository.findByArchived( true ) );
    }

    @Override
    public List<CategoryDto> getAllUnarchivedCategories() {
        return transformCategories( categoryRepository.findByArchived( false ) );
    }

    private List<CategoryDto> transformCategories( List<Category> categorys ) {
        return transformCategories( categorys.stream() );
    }

    private List<CategoryDto> transformCategories( Iterable<Category> categorys ) {
        return transformCategories( StreamSupport.stream( categorys.spliterator(), false ) );
    }

    private List<CategoryDto> transformCategories( Stream<Category> categorysStream ) {

        List<CategoryDto> transformedCategories = categorysStream
            .map( DtoTransformer::transform )
            .sorted( ( c1, c2 ) -> c1.getName().compareTo( c2.getName() ) )
            .collect( Collectors.toList() );

        return transformedCategories;
    }
}
