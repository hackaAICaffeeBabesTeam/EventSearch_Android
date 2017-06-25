package ca.caffee.eventsearch.calendar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import ca.caffee.eventsearch.events.EventEventsRefreshed;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by mtajc on 24.06.2017.
 */

public class EventManager {
  private static final String TAG = EventManager.class.getSimpleName();
  static ContentResolver contentResolver;

  public EventManager(Context ctx) {
    contentResolver = ctx.getContentResolver();
  }

  public static final String[] CALENDAR_FIELDS = {
      CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CalendarContract.Calendars.NAME,
      CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.OWNER_ACCOUNT, CalendarContract.Calendars.VISIBLE,
      CalendarContract.Calendars.SYNC_EVENTS, CalendarContract.Calendars.CALENDAR_COLOR, CalendarContract.Calendars.CALENDAR_COLOR_KEY,
      CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CALENDAR_TIME_ZONE, CalendarContract.Calendars.IS_PRIMARY
  };
  public static final String[] CALENDAR_FIELDS_OLD_API = {
      CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CalendarContract.Calendars.NAME,
      CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.OWNER_ACCOUNT, CalendarContract.Calendars.VISIBLE,
      CalendarContract.Calendars.SYNC_EVENTS, CalendarContract.Calendars.CALENDAR_COLOR, CalendarContract.Calendars.CALENDAR_COLOR_KEY,
      CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CALENDAR_TIME_ZONE
  };

  public static final String[] EVENT_FIELDS = {
      CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ALL_DAY, CalendarContract.Events.AVAILABILITY,
      CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.DESCRIPTION, "end", "begin", CalendarContract.Events.DURATION,
      CalendarContract.Events.EVENT_COLOR, CalendarContract.Events.EVENT_END_TIMEZONE, CalendarContract.Events.EVENT_TIMEZONE,
      CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, CalendarContract.Events.GUESTS_CAN_MODIFY,
      CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, CalendarContract.Events.HAS_ALARM, CalendarContract.Events.HAS_ATTENDEE_DATA,
      CalendarContract.Events.HAS_EXTENDED_PROPERTIES, CalendarContract.Events.LAST_DATE, CalendarContract.Events.ORGANIZER,
      CalendarContract.Events.ORIGINAL_ALL_DAY, CalendarContract.Events.SELF_ATTENDEE_STATUS, CalendarContract.Events.STATUS,
      CalendarContract.Events.TITLE, CalendarContract.Events._ID, "event_id", CalendarContract.Events.DISPLAY_COLOR,
      CalendarContract.Events.IS_ORGANIZER
  };

  public static ArrayList<CalendarObject> getCalendars(Context context) {
    ArrayList<CalendarObject> calendars = new ArrayList<>();
    contentResolver = context.getContentResolver();
    Cursor cursor;
    try {
      if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return null;
      }
      cursor = contentResolver.query(CalendarContract.Calendars.CONTENT_URI, CALENDAR_FIELDS, null, null, null);
    } catch (SQLiteException e) {
      Log.e(TAG, "getCalendars (no account?): " + e.getLocalizedMessage());
      //TODO handle error, dialog?
      return calendars;
    }
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        calendars.add(CalendarObject.getCalendarObject(cursor));
      }
    }
    try {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    } catch (Exception e) {
      Log.e(TAG, "getCalendars problem with: " + e.getLocalizedMessage());
    }
    return calendars;
  }

  public static ArrayList<Event> getEventList(Context context, int calendarId, long timeStart, long timeEnd) {
    Cursor cursor;
    ArrayList<Event> events = new ArrayList<>();
    try {
      cursor = context.getContentResolver()
          .query(Uri.parse("content://com.android.calendar/instances/when/" + timeStart + "/" + timeEnd), EVENT_FIELDS, "calendar_id = " + calendarId,
              null, null);
    } catch (SQLiteException e) {
      Log.e(TAG, "getEventList (no account?): " + e.getLocalizedMessage());
      //TODO handle error, dialog?
      return events;
    }

    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        events.add(Event.getEventObject(cursor));
      }
    }

    try {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    } catch (Exception e) {
      Log.e(TAG, "getEventList problem with: " + e.getLocalizedMessage());
    }
    return events;
  }

  public static void allEventsCalendars(ArrayList<CalendarObject> calendarObjects, Context context) {
    for (CalendarObject calendarObject : calendarObjects) {
      if (calendarObject != null && (calendarObject.accessLevel == 700 || calendarObject.accessLevel == 800)) {
        //My local calendars
        getEvents(calendarObject.id, context);
      } else if (calendarObject != null && calendarObject.accessLevel == 200) {
        //Friends calendar
        //getEvents(calendarObject.id);
      }
    }
  }

  public static void getEvents(int calendarId, Context context) {
    long timeStart = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7);
    long timeEnd = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
    ArrayList<Event> eventList = EventManager.getEventList(context, calendarId, timeStart, timeEnd);
    EventBus.getDefault().post(new EventEventsRefreshed(eventList));
  }
}
