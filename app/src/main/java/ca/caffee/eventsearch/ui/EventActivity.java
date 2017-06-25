package ca.caffee.eventsearch.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.ui.lists.ItemEventGoing;
import ca.caffee.eventsearch.ui.lists.ItemEventSmall;
import ca.caffee.eventsearch.ui.lists.ItemEventTop;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.parceler.Parcels;

public class EventActivity extends AppCompatActivity {
  private static final String TAG = EventActivity.class.getSimpleName();
  @BindView(R.id.image) ImageView imageView;
  @BindView(R.id.title) TextView textViewTitle;
  @BindView(R.id.description) TextView textViewDescription;
  @BindView(R.id.mainRelative) RelativeLayout mainRelative;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event);
    ButterKnife.bind(this);
    if (getIntent() != null && getIntent().hasExtra("eventSmall")) {
      ItemEventSmall itemEventSmall = Parcels.unwrap(getIntent().getParcelableExtra("eventSmall"));
      setEvent(itemEventSmall.urlFake, itemEventSmall.event.title, itemEventSmall.event.description);
      Log.d(TAG, "onCreate: ");
    } else if (getIntent() != null && getIntent().hasExtra("eventTop")) {
      ItemEventTop itemEventTop = Parcels.unwrap(getIntent().getParcelableExtra("eventTop"));
      setEvent(itemEventTop.urlFake, itemEventTop.event.title, itemEventTop.event.description);
      Log.d(TAG, "onCreate: ");
    } else if (getIntent() != null && getIntent().hasExtra("eventGoing")) {
      ItemEventGoing itemEventGoing = Parcels.unwrap(getIntent().getParcelableExtra("eventGoing"));
      setEvent(itemEventGoing.urlFake, itemEventGoing.title, itemEventGoing.desc);
      Log.d(TAG, "onCreate: ");
    }
    Log.d(TAG, "onCreate: ");
  }

  @OnClick(R.id.fab) public void onClickFab() {
    try {
      Intent intent = getPackageManager().getLaunchIntentForPackage("com.stubhub");
      startActivity(intent);
    } catch (Exception e) {
      //No stubhub app
      Intent intent = new Intent(Intent.ACTION_VIEW,
          Uri.parse("https://www.stubhub.com/toronto-fc-tickets-toronto-fc-toronto-bmo-field-6-27-2017/event/103008565/"));
      startActivity(intent);
    }
  }

  private Target target = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      if (bitmap != null && !bitmap.isRecycled()) {
        Palette palette = Palette.from(bitmap).generate();
        imageView.setImageBitmap(bitmap);
        //mainRelative.setBackgroundColor(palette.getMutedColor(Color.GRAY));
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(palette.getDominantColor(ContextCompat.getColor(EventActivity.this, R.color.colorPrimary))));
        //getWindow().setNavigationBarColor(palette.getDarkVibrantColor(ContextCompat.getColor(EventActivity.this, R.color.colorPrimaryDark)));
      }
    }

    @Override public void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
  };

  private void setEvent(String url, String title, String description) {
    Picasso.with(this).load(url).into(target);
    if (title != null) {
      textViewTitle.setText(title);
    }
    if (description != null) {
      textViewDescription.setText(description);
    }
  }
}