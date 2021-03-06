package com.skilldistillery.roundtablegaming.controllers;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skilldistillery.roundtablegaming.data.AddressDAO;
import com.skilldistillery.roundtablegaming.data.EventCommentDAO;
import com.skilldistillery.roundtablegaming.data.EventDAO;
import com.skilldistillery.roundtablegaming.data.GameDAO;
import com.skilldistillery.roundtablegaming.data.UserDAO;
import com.skilldistillery.roundtablegaming.entities.Address;
import com.skilldistillery.roundtablegaming.entities.Attendee;
import com.skilldistillery.roundtablegaming.entities.Event;
import com.skilldistillery.roundtablegaming.entities.EventComment;
import com.skilldistillery.roundtablegaming.entities.Game;
import com.skilldistillery.roundtablegaming.entities.User;

@Controller
public class EventController {
	
	@Autowired
	private EventDAO dao;
	
	@Autowired
	private GameDAO gameDao;
	
	@Autowired
	private AddressDAO addrDao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private EventCommentDAO commentDao;

	@GetMapping("getAllEvents.do")
	public String getAllEvents(Model model) {
		List<Event> events = dao.getAllEvents();
		List<Event> pastEvents = new ArrayList<>();
		List<Event> futureEvents = new ArrayList<>();
		List<Address> eventAddresses = addrDao.getAddressesForAllEvents();
		String zipCodes = "";
		
		for (Event event : events) {
			if (LocalDate.now().isAfter(event.getEventDate())) {
				pastEvents.add(event);
			}
			else {
				futureEvents.add(event);
			}
		}
		
		for (int i=0; i < eventAddresses.size(); i++) {
			if (i == eventAddresses.size() - 1) {
				zipCodes += eventAddresses.get(i).getZipCode();
			}
			else {
				zipCodes += eventAddresses.get(i).getZipCode()+"|";
			}
		}
		
		model.addAttribute("pastEvents", pastEvents);
		model.addAttribute("futureEvents", futureEvents);
		model.addAttribute("allEvents", events);
		model.addAttribute("zipCodes", zipCodes);
		return "events";
	}
	
	@GetMapping("rpg.do")
	public String rpg(Model model) {
		List<Event> rpgEvents = dao.getEventsByCategory("Tabletop RPG");
		List<Event> futureEvents = new ArrayList<>();
		
		for (Event event : rpgEvents) {
			if (LocalDate.now().isBefore(event.getEventDate())) {
				futureEvents.add(event);
			}
		}
		
		model.addAttribute("category", futureEvents);
		return "rpg";
	}
	
	@GetMapping("miniature.do")
	public String mini(Model model) {
		List<Event> miniEvents = dao.getEventsByCategory("Miniatures");
		List<Event> futureEvents = new ArrayList<>();
		
		for (Event event : miniEvents) {
			if (LocalDate.now().isBefore(event.getEventDate())) {
				futureEvents.add(event);
			}
		}
		
		model.addAttribute("category", futureEvents);
		return "miniature";
	}
	
	@GetMapping("tcg.do")
	public String tcg(Model model) {
		List<Event> tcgEvents = dao.getEventsByCategory("Trading Card Games");
		List<Event> futureEvents = new ArrayList<>();
		
		for (Event event : tcgEvents) {
			if (LocalDate.now().isBefore(event.getEventDate())) {
				futureEvents.add(event);
			}
		}
		
		model.addAttribute("category", futureEvents);
		return "tcg";
	}
	
	@GetMapping("searchEventsByGame.do")
	public String searchEventsByGame(@RequestParam String title, Model model) {
		List<Event> eventResults = dao.getEventsByGame(title);
		model.addAttribute("events", eventResults);
		return "#";
	}
	
	@GetMapping("getAttendees.do")
	public String getAttendees(int eventId, Model model) {
		Event event = dao.getEventById(eventId);
		List<Attendee> unfilteredAttendees = dao.getEventAttendees(event);
		List<Attendee> attendees = dao.filterUniqueAttendees(unfilteredAttendees);
		model.addAttribute("attendees", attendees);
		return "events";
	}
	
	@PostMapping("searchByDate.do")
	public String getEventsByDate(@RequestParam 
			@DateTimeFormat(iso = ISO.DATE) LocalDate date, HttpSession session) {
		List<Event> dateResults = dao.getEventsByDate(date);
		System.out.println(dateResults.get(0).toString());
		session.setAttribute("events", dateResults);
		return "#";
	}
	
	@GetMapping("createEventPage.do")
	public String createEvent(Model model) {
		List<Game> allGames = gameDao.getAllGames();
		model.addAttribute("games", allGames);
		return "createEvent";
	}
	
	@PostMapping("createEvent.do")
	public String create(Event newEvent, Model model, HttpSession session, @RequestParam ("eventGameIds") String[] eventGameIds) {
		System.out.println(Arrays.deepToString(eventGameIds));
		Integer[] gameIds = new Integer[eventGameIds.length];
	    for(int i=0; i < eventGameIds.length; i++) {
	    	
	    	try {
				gameIds[i] = Integer.parseInt(eventGameIds[i]);
			} catch (NumberFormatException e) {
				gameIds[i] = null;
			}
	    }
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		newEvent.setOrganizer(userDao.getUserById(loggedInUser.getId()));
//		System.out.println(newEvent.getEventGames());
		Event event = dao.createEvent(newEvent, gameIds);
		model.addAttribute("newEvent", event);
		return "account";
	}
	
	@PostMapping("updateEvent.do")
	public String update(Event event, Address address,  @RequestParam int id,  @RequestParam int addressId, 
			@RequestParam String[] eventGameIds, Model model, HttpSession session) {
		Integer[] gameIds = new Integer[eventGameIds.length];
	    for(int i=0; i < eventGameIds.length; i++) {
	    	
	    	try {
				gameIds[i] = Integer.parseInt(eventGameIds[i]);
			} catch (NumberFormatException e) {
				gameIds[i] = null;
			}
	    }
		System.out.println(event);
		System.out.println(address);
//		address.setId(addressId);
		User loggedInUser = (User) session.getAttribute("loggedInUser");
//		Address updAddress = addrDao.checkAddress(address);
		Event updEvent = dao.updateEvent(event, address, id, gameIds);
		model.addAttribute("updatedEvent", updEvent);
		session.setAttribute("loggedInUser", loggedInUser);
		return "redirect:account.do";
	}
	
	@GetMapping("eventsByGame.do")
	public String eventsByGame(@RequestParam String game, Model model) {
		List<Event> eventsByGame = dao.getEventsByGame(game);
		model.addAttribute("eventsOfGame",eventsByGame);
		return "gameEvents";
	}
	
	@GetMapping("singleEvent.do")
	public String getEvent(Integer id, Model model) {
		if (id != null) {
			Event event = dao.getEventById(id);
			List<EventComment> comments = commentDao.getEventCommentsByEventId(id);
			model.addAttribute("event", event);
			model.addAttribute("comments", comments);
			for (EventComment comment : comments) {
				System.out.println(comment);
			}
			return "thisEvent";
		}
			return "redirect:getAllEvents.do";
	}
	
	@PostMapping("searchBar.do")
	public String searchBar(String search, Model model) {
		List<Event> results = dao.getEventsByKeyword(search);
		model.addAttribute("results", results);
		return "searchedEvents";
	}
	
	@GetMapping("editEvent.do")
	public String editEvent(int id, Model model) {
		List<Game> allGames = gameDao.getAllGames();
		Event editEvent = dao.getEventById(id);
		model.addAttribute("eventToEdit", editEvent);
		model.addAttribute("eventGames", allGames);
		return "editEvent";
	}
	
	@PostMapping("deleteEvent.do")
	public String deleteEvent(int id, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		loggedInUser = userDao.getUserById(loggedInUser.getId());
		session.setAttribute("loggedInUser", loggedInUser);
		dao.disableEvent(id);
		System.out.println("\n\n\n"+id+"\n\n\n");
		return "redirect:account.do";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
		dateFormat.setLenient(true);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}
			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
			}
		});
		webDataBinder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:MM")));
			}
			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("HH:MM").format((LocalDate) getValue());
			}
		});

}
	}



