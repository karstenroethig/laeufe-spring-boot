package karstenroethig.laeufe.service;

import java.util.Collection;

import karstenroethig.laeufe.dto.CategoryDto;
import karstenroethig.laeufe.service.exceptions.CategoryAlreadyExistsException;


public interface CategoryService {

    public CategoryDto newCategory();

    public CategoryDto saveCategory( CategoryDto categoryDto ) throws CategoryAlreadyExistsException;

    public Boolean deleteCategory( Long categoryId );

    public CategoryDto editCategory( CategoryDto categoryDto ) throws CategoryAlreadyExistsException;

    public CategoryDto findCategory( Long categoryId );

    public Collection<CategoryDto> getAllCategories();

    public Collection<CategoryDto> getAllArchivedCategories();

    public Collection<CategoryDto> getAllUnarchivedCategories();
}
