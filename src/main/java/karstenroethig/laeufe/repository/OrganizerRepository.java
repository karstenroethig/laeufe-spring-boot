package karstenroethig.laeufe.repository;

import java.util.List;

import karstenroethig.laeufe.domain.Organizer;

import org.springframework.data.repository.CrudRepository;

public interface OrganizerRepository extends CrudRepository<Organizer, Long>
{
	List<Organizer> findByNameIgnoreCase( String name );

	List<Organizer> findByArchived( boolean archived );
}
