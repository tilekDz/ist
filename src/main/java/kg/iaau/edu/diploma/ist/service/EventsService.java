package kg.iaau.edu.diploma.ist.service;

import kg.iaau.edu.diploma.ist.entity.Events;
import kg.iaau.edu.diploma.ist.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {
    private final EventsRepository eventsRepository;

    @Autowired
    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public void save(Events events) {
        this.eventsRepository.save(events);
    }

    public void delete(Events events) {
        events.setActive(false);
        this.eventsRepository.save(events);
    }

    public Page<Events> getAllEvents(Specification specification, Pageable pageable) {
        return this.eventsRepository.findAll(specification, pageable);
    }

    public Events getById(Long id) {
        return this.eventsRepository.findById(id).orElse(null);
    }

    public List<Events> findLastThreeEvents() {
        return this.eventsRepository.findLastThreeEvents();
    }
}
