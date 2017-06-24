package ca.caffee.eventsearch.calendar;

/**
 * Created by mtajc on 24.06.2017.
 */

public class EventSimple {
  public long eventId;
  public long dateStart;
  public long dateEnd;
  public String title;
  public String location;
  public int status;
  public int color;
  public int allDay;

  public EventSimple(long eventId, long dateStart, String title, long dateEnd) {
    this.eventId = eventId;
    this.dateStart = dateStart;
    this.title = title;
    this.dateEnd = dateEnd;
  }
}
