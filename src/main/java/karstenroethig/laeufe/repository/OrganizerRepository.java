package karstenroethig.laeufe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import karstenroethig.laeufe.domain.Organizer;

public interface OrganizerRepository extends CrudRepository<Organizer, Long>
{
	List<Organizer> findByNameIgnoreCase(String name);

	List<Organizer> findByArchived(Boolean archived);
}
