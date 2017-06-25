package ca.caffee.eventsearch.ui.lists;

import ca.caffee.eventsearch.calendar.Event;
import org.parceler.Parcel;

/**
 * Created by mtajc on 25.06.2017.
 */

@Parcel public class ItemEventSmall implements AdapterItem {
  public Event event;
  public String urlFake;
  public String distance;
  public String price;

  public ItemEventSmall() {
  }

  public ItemEventSmall(Event event) {
    this.event = event;

    if (event.url != null) {
      this.urlFake = event.url;
    } else {
      this.urlFake = RandomGenerator.getUrlImage();
    }
    if (event.distance != null) {
      this.distance = event.distance;
    } else {
      this.distance = RandomGenerator.getDistance();
    }
    if (event.price != null) {
      this.price = event.price;
    } else {
      this.price = RandomGenerator.getPrice();
    }
  }

  @Override public int getViewType() {
    return 1;
  }
}
