package com.skilldistillery.roundtablegaming.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.roundtablegaming.entities.Attendee;
import com.skilldistillery.roundtablegaming.entities.AttendeeId;

@Service
@Transactional
public class AttendeeDAOImpl implements AttendeeDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Attendee create(Attendee attn) {
		em.persist(attn);
		em.flush();
		return attn;
	}

	@Override
	public Attendee update(Attendee attn) {
		Attendee updated = em.find(Attendee.class, attn.getId());
		if (updated != null) {
			updated.setUser(attn.getUser());
			updated.setEventGame(attn.getEventGame());
			updated.setEventRating(attn.getEventRating());
			updated.setAttendeeComment(attn.getAttendeeComment());
			em.persist(updated);
			em.flush();
		}
		return updated;
	}

	@Override
	public boolean enable(AttendeeId id) {
		Attendee enabled = em.find(Attendee.class, id);
		if (enabled != null) {
			enabled.setEnabled(true);
			em.persist(enabled);
			em.flush();
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean disable(AttendeeId id) {
		Attendee disabled = em.find(Attendee.class, id);
		if (disabled != null) {
			disabled.setEnabled(false);
			em.persist(disabled);
			em.flush();
			return true;
		}
		else {
			return false;
		}
	}

}
