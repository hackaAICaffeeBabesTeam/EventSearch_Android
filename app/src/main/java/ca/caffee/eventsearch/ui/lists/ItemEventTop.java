package ca.caffee.eventsearch.ui.lists;

import ca.caffee.eventsearch.calendar.Event;
import org.parceler.Parcel;

/**
 * Created by mtajc on 25.06.2017.
 */

@Parcel
public class ItemEventTop implements AdapterItem {
  public Event event;
  public String urlFake;
  public String distance;
  public String price;

  public ItemEventTop() {
  }

  public ItemEventTop(Event event) {
    this.event = event;
    this.urlFake = RandomGenerator.getUrlImage();
    this.distance = RandomGenerator.getDistance();
    this.price = RandomGenerator.getPrice();
  }

  @Override public int getViewType() {
    return 0;
  }
}
