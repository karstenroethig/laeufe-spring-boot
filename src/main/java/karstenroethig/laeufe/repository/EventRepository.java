package karstenroethig.laeufe.repository;

import karstenroethig.laeufe.domain.Event;

import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, Long> {
}
