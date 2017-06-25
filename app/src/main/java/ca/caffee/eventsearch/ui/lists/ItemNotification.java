package ca.caffee.eventsearch.ui.lists;

import org.parceler.Parcel;

/**
 * Created by mtajc on 25.06.2017.
 */

@Parcel public class ItemNotification implements AdapterItem {
  public long timestamp;
  public String title;
  public String desc;
  public int image;

  public ItemNotification() {
    this.title = "Adventure suggestion";
    this.desc = "GM Bot suggested event for you in ...";
    timestamp = System.currentTimeMillis();
  }

  @Override public int getViewType() {
    return 3;
  }
}
