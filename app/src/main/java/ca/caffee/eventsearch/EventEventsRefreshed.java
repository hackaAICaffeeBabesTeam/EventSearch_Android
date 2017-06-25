package ca.caffee.eventsearch;

import ca.caffee.eventsearch.calendar.Event;
import java.util.ArrayList;

/**
 * Created by mtajc on 24.06.2017.
 */

public class EventEventsRefreshed {
  ArrayList<Event> events = new ArrayList<>();

  public EventEventsRefreshed(ArrayList<Event> events) {
    this.events = events;
  }
}
