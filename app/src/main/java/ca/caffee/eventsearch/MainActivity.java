package ca.caffee.eventsearch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  private TextView mTextMessage;
  private EventManager eventManager;
  private static final int requestCodeCalendar = 123;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_home:
              mTextMessage.setText(R.string.title_home);
              return true;
            case R.id.navigation_dashboard:
              mTextMessage.setText(R.string.title_dashboard);
              return true;
            case R.id.navigation_notifications:
              mTextMessage.setText(R.string.title_notifications);
              return true;
          }
          return false;
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    eventManager = new EventManager(this);
  }

  @Override protected void onResume() {
    super.onResume();
    ArrayList<CalendarObject> calendars = EventManager.getCalendars(this);
    if (calendars == null) {
      ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CALENDAR }, requestCodeCalendar);
    } else {
      allEventsCalendars(calendars);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == requestCodeCalendar) {
      for (int i = 0; i < permissions.length; i++) {
        String permission = permissions[i];
        int grantResult = grantResults[i];

        if (permission.equals(Manifest.permission.READ_CALENDAR)) {
          if (grantResult == PackageManager.PERMISSION_GRANTED) {
            ArrayList<CalendarObject> calendars = EventManager.getCalendars(this);
            allEventsCalendars(calendars);
          } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_CALENDAR }, requestCodeCalendar);
          }
        }
      }
    }
  }

  private void allEventsCalendars(ArrayList<CalendarObject> calendarObjects) {
    for (CalendarObject calendarObject : calendarObjects) {
      if (calendarObject != null && (calendarObject.accessLevel == 700 || calendarObject.accessLevel == 800)) {
        //My local calendars
        getEvents(calendarObject.id);
      } else if (calendarObject != null && calendarObject.accessLevel == 200) {
        //Friends calendar
      }
    }
  }

  private void getEvents(int calendarId) {
    long timeStart = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7);
    long timeEnd = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
    ArrayList<Event> eventList = EventManager.getEventList(this, calendarId, timeStart, timeEnd);
    Log.d(TAG, "getEvents: ");
  }
}
