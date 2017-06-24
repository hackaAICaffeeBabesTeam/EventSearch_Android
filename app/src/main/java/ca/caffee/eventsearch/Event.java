package ca.caffee.eventsearch;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by mtajc on 24.06.2017.
 */

public class Event {
  private static final String TAG = Event.class.getSimpleName();
  public long id;
  public long eventId;
  public int accessLevel;
  public int allDay;
  public int availability;
  public int calendarId;
  public int canInvite;
  public int colorDisplay;
  public long dtStart;
  public long dtEnd;
  public String duration;
  public int color;
  public String timezoneEnd;
  public String timezoneStart;
  public String description;
  public int guestsSeeGuests;
  public int hasAlarm;
  public int hasAttendeeData;
  public int hasExtendedProperies;
  public long lastEventRepetition;
  public int lastSynced;
  public String organizerEmail;
  public int originalAllDay;
  public int selfStatus;
  public int status;
  public String title;
  public String isOrganizer;

  public String location;
  public int guestsInviteOthers;
  public int guestsModify;

  public static Event getEventObject(Cursor cursor) {
    Event eventNew = new Event();

    eventNew.accessLevel = cursor.getInt(0);
    eventNew.allDay = cursor.getInt(1);
    eventNew.availability = cursor.getInt(2);
    eventNew.calendarId = cursor.getInt(3);
    //eventNew.canInvite = cursor.getInt(4);
    eventNew.description = cursor.getString(4);
    eventNew.dtEnd = cursor.getLong(5);
    eventNew.dtStart = cursor.getLong(6);
    eventNew.duration = cursor.getString(7);
    eventNew.color = cursor.getInt(8);
    eventNew.timezoneEnd = cursor.getString(9);
    eventNew.timezoneStart = cursor.getString(10);
    eventNew.location = cursor.getString(11);
    eventNew.guestsInviteOthers = cursor.getInt(12);
    eventNew.guestsModify = cursor.getInt(13);
    eventNew.guestsSeeGuests = cursor.getInt(14);
    eventNew.hasAlarm = cursor.getInt(15);
    eventNew.hasAttendeeData = cursor.getInt(16);
    eventNew.hasExtendedProperies = cursor.getInt(17);
    eventNew.lastEventRepetition = cursor.getLong(18);
    //eventNew.lastSynced = cursor.getInt(16);
    eventNew.organizerEmail = cursor.getString(19);
    eventNew.originalAllDay = cursor.getInt(20);
    eventNew.selfStatus = cursor.getInt(21);
    eventNew.status = cursor.getInt(22);
    eventNew.title = cursor.getString(23);
    eventNew.id = cursor.getLong(24);
    eventNew.eventId = cursor.getLong(25);

    Calendar calendar;
    TimeZone timeZone;
    if (eventNew.timezoneStart != null && !"".equals(eventNew.timezoneStart)) {
      try {
        timeZone = TimeZone.getTimeZone(eventNew.timezoneStart);
        calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(eventNew.dtStart);
        eventNew.dtStart = calendar.getTimeInMillis();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (eventNew.timezoneEnd != null && !"".equals(eventNew.timezoneEnd)) {

      try {
        timeZone = TimeZone.getTimeZone(eventNew.timezoneEnd);
        calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(eventNew.dtEnd);
        eventNew.dtEnd = calendar.getTimeInMillis();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      eventNew.colorDisplay = cursor.getInt(26);
    }
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      eventNew.isOrganizer = cursor.getString(27);
    }
    return eventNew;
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    if (((Event) obj).id == this.id) {
      return true;
    }
    return false;
  }

  public Calendar getCalendarStart() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(this.dtStart);
    return calendar;
  }

  public Calendar getCalendarEnd() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(this.dtEnd);
    return calendar;
  }

  public Location getLocationGps(Geocoder geocoder) {
    if (this.location == null || geocoder == null || this.location.length() < 2 || "".equals(this.location)) {
      return null;
    }
    try {
      List<Address> address = geocoder.getFromLocationName(this.location, 1);
      if (address.size() > 0 && address.get(0) != null) {
        Location result = new Location("");
        result.setLatitude(address.get(0).getLatitude());
        result.setLongitude(address.get(0).getLongitude());
        return result;
      }
    } catch (IOException e) {
      Log.e(TAG, "getLocationGps :" + e.getLocalizedMessage());
      return null;
    }
    return null;
  }

  public float distanceBetween(Event event, Geocoder geocoder) {
    Location local = this.getLocationGps(geocoder);
    if (local == null) {
      return 0;
    }
    Location remote = event.getLocationGps(geocoder);
    if (remote == null) {
      return 0;
    }
    return local.distanceTo(remote);
  }

  public float distanceBetween(Location local, Location remote) {
    if (local == null) {
      return 0;
    }
    if (remote == null) {
      return 0;
    }
    return local.distanceTo(remote);
  }

  public boolean isAtLeastDayLong() {
    if (TimeUnit.HOURS.convert(dtEnd - dtStart, TimeUnit.MILLISECONDS) < 24) {
      return false;
    }
    return true;
  }

  public EventSimple getSimple() {
    return new EventSimple(this.eventId, this.dtStart, this.title, this.dtEnd);
  }

  @Override public String toString() {
    return "Event{"
        + "id="
        + id
        + ", accessLevel="
        + accessLevel
        + ", allDay="
        + allDay
        + ", availability="
        + availability
        + ", calendarId="
        + calendarId
        + ", canInvite="
        + canInvite
        + ", colorDisplay="
        + colorDisplay
        + ", dtStart="
        + dtStart
        + ", dtEnd="
        + dtEnd
        + ", duration='"
        + duration
        + '\''
        + ", color="
        + color
        + ", timezoneEnd='"
        + timezoneEnd
        + '\''
        + ", timezoneStart='"
        + timezoneStart
        + '\''
        + ", description='"
        + description
        + '\''
        + ", guestsSeeGuests="
        + guestsSeeGuests
        + ", hasAlarm="
        + hasAlarm
        + ", hasAttendeeData="
        + hasAttendeeData
        + ", hasExtendedProperies="
        + hasExtendedProperies
        + ", lastEventRepetition="
        + lastEventRepetition
        + ", lastSynced="
        + lastSynced
        + ", organizerEmail='"
        + organizerEmail
        + '\''
        + ", originalAllDay="
        + originalAllDay
        + ", selfStatus="
        + selfStatus
        + ", status="
        + status
        + ", title='"
        + title
        + '\''
        + ", isOrganizer='"
        + isOrganizer
        + '\''
        + ", location='"
        + location
        + '\''
        + ", guestsInviteOthers="
        + guestsInviteOthers
        + ", guestsModify="
        + guestsModify
        + '}';
  }
}
