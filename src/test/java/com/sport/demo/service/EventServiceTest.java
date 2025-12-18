package com.sport.demo.service;

import com.sport.demo.dto.event.EventResponse;
import com.sport.demo.entity.Event;
import com.sport.demo.entity.EventType;
import com.sport.demo.entity.User;
import com.sport.demo.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void mapsOrganizerAndTypeIntoResponse() {
        EventType type = new EventType();
        type.setName("Tournament");

        User organizer = new User();
        organizer.setUsername("john_doe");

        Event event = new Event();
        event.setEventID(10);
        event.setName("Spring Cup");
        event.setDescription("Best teams");
        event.setOrganizer(organizer);
        event.setEventType(type);

        when(eventRepository.findAllByOrderByEventIDDesc()).thenReturn(List.of(event));

        List<EventResponse> responses = eventService.getAllEvents();

        assertEquals(1, responses.size());
        EventResponse response = responses.get(0);
        assertEquals(10, response.getId());
        assertEquals("Spring Cup", response.getName());
        assertEquals("Best teams", response.getDescription());
        assertEquals("john_doe", response.getOrganizerName());
        assertEquals("Tournament", response.getEventType());
        assertEquals("DEC 15", response.getDate());
        assertEquals("JOIN", response.getStatus());
        assertFalse(response.getRecommended());
    }

    @Test
    void fallsBackToUnknownNamesWhenDataMissing() {
        Event event = new Event();
        event.setEventID(5);
        event.setName("Mystery Event");
        event.setDescription("No details");
        event.setOrganizer(null);
        event.setEventType(null);

        when(eventRepository.findAllByOrderByEventIDDesc()).thenReturn(List.of(event));

        List<EventResponse> responses = eventService.getAllEvents();

        EventResponse response = responses.get(0);
        assertEquals("Unknown", response.getOrganizerName());
        assertEquals("Unknown", response.getEventType());
    }
}
