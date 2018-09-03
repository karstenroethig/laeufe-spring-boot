package karstenroethig.laeufe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import karstenroethig.laeufe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
	List<Category> findByNameIgnoreCase(String name);

	List<Category> findByArchived(Boolean archived);
}
