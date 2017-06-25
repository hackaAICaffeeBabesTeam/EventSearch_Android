package ca.caffee.eventsearch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.ui.lists.ItemEventGoing;
import ca.caffee.eventsearch.ui.lists.ItemEventSmall;
import ca.caffee.eventsearch.ui.lists.ItemEventTop;
import org.parceler.Parcels;

public class EventActivity extends AppCompatActivity {
  private static final String TAG = EventActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event);
    if (getIntent() != null && getIntent().hasExtra("eventSmall")) {
      ItemEventSmall itemEventSmall = Parcels.unwrap(getIntent().getParcelableExtra("eventSmall"));
      Log.d(TAG, "onCreate: ");
    } else if (getIntent() != null && getIntent().hasExtra("eventTop")) {
      ItemEventTop itemEventTop = Parcels.unwrap(getIntent().getParcelableExtra("eventTop"));
      Log.d(TAG, "onCreate: ");
    } else if (getIntent() != null && getIntent().hasExtra("eventGoing")) {
      ItemEventGoing itemEventGoing = Parcels.unwrap(getIntent().getParcelableExtra("eventGoing"));
      Log.d(TAG, "onCreate: ");
    }
    Log.d(TAG, "onCreate: ");
  }
}
