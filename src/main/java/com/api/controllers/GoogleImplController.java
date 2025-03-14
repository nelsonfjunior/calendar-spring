package com.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.DTO.EventDTO;
import com.api.services.GoogleAuthService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

@RestController
@RequestMapping("/calendar")
public class GoogleImplController {
    
    @Autowired
	private  GoogleAuthService googleAuthService;

    @PostMapping("/add-event")
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            Calendar client = googleAuthService.getCalendarClient();
            if (client == null) {
                return new ResponseEntity<>("Google Calendar client não inicializado.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Event event = new Event()
                    .setSummary(eventDTO.summary())
                    .setLocation(eventDTO.location())
                    .setDescription(eventDTO.description());

            DateTime startDateTime = new DateTime(eventDTO.startTime());
            EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("America/Sao_Paulo");
            event.setStart(start);

            DateTime endDateTime = new DateTime(eventDTO.endTime());
            EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Sao_Paulo");
            event.setEnd(end);

            event = client.events().insert("primary", event).execute();
            return new ResponseEntity<>("Evento criado: " + event.getHtmlLink(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar evento: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list-events")
    public ResponseEntity<?> listEvents() {
        try {
            Calendar client = googleAuthService.getCalendarClient();
            if (client == null) {
                return new ResponseEntity<>("Google Calendar client não inicializado.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Events events = client.events().list("primary").setMaxResults(10).setOrderBy("startTime").setSingleEvents(true).execute();
            return new ResponseEntity<>(events.getItems(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao listar eventos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
