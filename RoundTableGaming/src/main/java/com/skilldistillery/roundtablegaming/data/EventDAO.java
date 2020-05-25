package com.skilldistillery.roundtablegaming.data;

import java.time.LocalDate;
import java.util.List;

import com.skilldistillery.roundtablegaming.entities.Address;
import com.skilldistillery.roundtablegaming.entities.Attendee;
import com.skilldistillery.roundtablegaming.entities.Event;
import com.skilldistillery.roundtablegaming.entities.Game;

public interface EventDAO {

	public Event createEvent(Event event);
	
	public List<Event> getAllEvents();
	
	public Event getEventById(int id);
	
	public List<Event> getEventsByAddress(Address address);
	
	public List<Event> getEventsByCategory(String category);
	
	public List<Event> getEventsByGame(Game game);
	
	public List<Event> getEventsByDate(LocalDate date);
	
	public List<Event> getEventsByKeyword(String keyword);
	
	public List<Attendee> getEventAttendees(Event event);
	
	public List<Attendee> filterUniqueAttendees(List<Attendee> aList);
	
	public Event updateEvent(Event updatedEvent, int id);
	
	public boolean enableEvent(int id);
	
	public boolean disableEvent(int id);
	
}
