package ca.caffee.eventsearch.ui.lists;

import ca.caffee.eventsearch.calendar.Event;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderEventTop implements AdapterItem {
  public Event event;

  public ViewHolderEventTop(Event event) {
    this.event = event;
  }

  @Override public int getViewType() {
    return 0;
  }
}
