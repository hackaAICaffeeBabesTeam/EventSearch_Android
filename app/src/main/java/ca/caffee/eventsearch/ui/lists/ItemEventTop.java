package ca.caffee.eventsearch.ui.lists;

import ca.caffee.eventsearch.calendar.Event;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ItemEventTop implements AdapterItem {
  public Event event;
  public String urlFake;

  public ItemEventTop(Event event) {
    this.event = event;
    this.urlFake = RandomImageGenerator.getUrlImage();
  }

  @Override public int getViewType() {
    return 0;
  }
}
