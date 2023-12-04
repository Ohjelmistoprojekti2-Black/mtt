package com.op2.op2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.op2.op2.domain.Event;
import com.op2.op2.domain.EventRepository;

@DataJpaTest
public class EventRepositoryTests {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void findByEventNameShouldReturnListSize() {
        Event event = new Event("Some test event name", LocalDate.now(), null, "test description", 100,
                "Mannerheimintie 1");
        eventRepository.save(event);
        List<Event> eventsByName = eventRepository.findByEventName("Some test event name");
        assertThat(eventsByName).hasSize(1);
    }

    @Test
    public void saveAndDeleteEvent() {
        Event event = new Event("Test event name", LocalDate.now(), null, "test description", 100, "Mannerheimintie 1");
        eventRepository.save(event);

        Optional<Event> savedEvent = eventRepository.findById(event.getEventId());
        assertThat(savedEvent).isPresent();

        eventRepository.delete(event);

        Optional<Event> deletedEvent = eventRepository.findById(event.getEventId());
        assertThat(deletedEvent).isEmpty();
    }
}
