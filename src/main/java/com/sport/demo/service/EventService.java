package com.sport.demo.service;

import com.sport.demo.dto.event.EventResponse;
import com.sport.demo.entity.Event;
import com.sport.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAllByOrderByEventIDDesc();
        return events.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EventResponse mapToResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getEventID());
        response.setName(event.getName());
        response.setDescription(event.getDescription());
        response.setDate("DEC 15"); // TODO: Map from actual date field
        response.setStatus("JOIN");
        response.setRecommended(false); // TODO: Add logic for recommended
        response.setOrganizerName(event.getOrganizer() != null ? event.getOrganizer().getUsername() : "Unknown");
        response.setEventType(event.getEventType() != null ? event.getEventType().getName() : "Unknown");
        return response;
    }
}
