package karstenroethig.laeufe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import karstenroethig.laeufe.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Long>
{
	List<Country> findByNameIgnoreCase(String name);

	List<Country> findByArchived(Boolean archived);
}
