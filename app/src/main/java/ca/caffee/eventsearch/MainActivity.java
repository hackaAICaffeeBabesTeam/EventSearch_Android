package ca.caffee.eventsearch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.calendar.CalendarObject;
import ca.caffee.eventsearch.calendar.Event;
import ca.caffee.eventsearch.calendar.EventManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
  private EventManager eventManager;
  private static final int requestCodeCalendar = 123;
  private Fragment currentFragment;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_events:
              if (currentFragment != null && currentFragment instanceof EventListFragment) {
                currentFragment = new EventListFragment();
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .addToBackStack("events")
                    .commitAllowingStateLoss();
              }
              return true;
            case R.id.navigation_tickets:
              if (currentFragment != null && currentFragment instanceof EventListFragment) {
                currentFragment = new EventListFragment();
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .addToBackStack("tickets")
                    .commitAllowingStateLoss();
              }
              return true;
            case R.id.navigation_other:
              if (currentFragment != null && currentFragment instanceof EventListFragment) {
                currentFragment = new EventListFragment();
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .addToBackStack("other")
                    .commitAllowingStateLoss();
              }
              return true;
          }
          return false;
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    eventManager = new EventManager(this);
    if (savedInstanceState == null) {
      currentFragment = new EventListFragment();
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, currentFragment).commitAllowingStateLoss();
    }
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

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.top_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        return true;
      case R.id.action_search:
        Intent intent = new Intent(this, SearchActivity.class);
        MainActivity.this.startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void allEventsCalendars(ArrayList<CalendarObject> calendarObjects) {
    for (CalendarObject calendarObject : calendarObjects) {
      if (calendarObject != null && (calendarObject.accessLevel == 700 || calendarObject.accessLevel == 800)) {
        //My local calendars
        getEvents(calendarObject.id);
      } else if (calendarObject != null && calendarObject.accessLevel == 200) {
        //Friends calendar
        //getEvents(calendarObject.id);
      }
    }
  }

  private void getEvents(int calendarId) {
    long timeStart = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7);
    long timeEnd = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
    ArrayList<Event> eventList = EventManager.getEventList(this, calendarId, timeStart, timeEnd);
    Log.d(TAG, "getEvents: ");
    EventBus.getDefault().post(new EventEventsRefreshed(eventList));
  }
}
