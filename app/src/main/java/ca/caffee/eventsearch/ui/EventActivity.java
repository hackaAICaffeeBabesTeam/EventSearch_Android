package ca.caffee.eventsearch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.ui.lists.ItemEventSmall;
import org.parceler.Parcels;

public class EventActivity extends AppCompatActivity {
  private static final String TAG = EventActivity.class.getSimpleName();
  

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event);
    if(getIntent() != null && getIntent().hasExtra("eventSmall")) {
      ItemEventSmall itemEventSmall = Parcels.unwrap(getIntent().getParcelableExtra("eventSmall"));
      Log.d(TAG, "onCreate: ");
    }
  }
}
