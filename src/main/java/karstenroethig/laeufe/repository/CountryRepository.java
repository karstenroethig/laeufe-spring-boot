package karstenroethig.laeufe.repository;

import java.util.List;

import karstenroethig.laeufe.domain.Country;

import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findByNameIgnoreCase( String name );

    List<Country> findByArchived( boolean archived );
}
