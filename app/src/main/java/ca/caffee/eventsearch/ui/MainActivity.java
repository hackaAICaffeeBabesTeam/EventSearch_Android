package ca.caffee.eventsearch.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.calendar.CalendarObject;
import ca.caffee.eventsearch.calendar.EventManager;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
  private static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
  private static final int requestCodeCalendar = 123;
  private ArrayList<Fragment> fragments = new ArrayList<>();
  private Fragment currentFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);
    EventManager eventManager = new EventManager(this);
    setFragments();
    if (savedInstanceState == null) {
      currentFragment = fragments.get(0);
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, currentFragment).commitAllowingStateLoss();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    ArrayList<CalendarObject> calendars = EventManager.getCalendars(this);
    if (calendars == null) {
      ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CALENDAR }, requestCodeCalendar);
    } else {
      EventManager.allEventsCalendars(calendars, MainActivity.this);
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
            EventManager.allEventsCalendars(calendars, MainActivity.this);
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

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.navigation_events:
        if (currentFragment != null && !(currentFragment instanceof EventListFragment)) {
          currentFragment = fragments.get(0);
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment_container, currentFragment)
              .addToBackStack("events")
              .commitAllowingStateLoss();
        }
        return true;
      case R.id.navigation_tickets:
        if (currentFragment != null && !(currentFragment instanceof GoingFragment)) {
          currentFragment = fragments.get(1);
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment_container, currentFragment)
              .addToBackStack("going")
              .commitAllowingStateLoss();
        }
        return true;
      case R.id.navigation_other:
        if (currentFragment != null && !(currentFragment instanceof EventListFragment)) {
          currentFragment = fragments.get(2);
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.fragment_container, currentFragment)
              .addToBackStack("other")
              .commitAllowingStateLoss();
        }
        return true;
    }
    return false;
  }

  private void setFragments() {
    fragments.clear();
    fragments.add(EventListFragment.newInstance());
    fragments.add(GoingFragment.newInstance());
    fragments.add(EventListFragment.newInstance());
  }
}
