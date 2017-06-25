package ca.caffee.eventsearch.ui.lists;

import org.parceler.Parcel;

/**
 * Created by mtajc on 25.06.2017.
 */

@Parcel
public class ItemEventGoing implements AdapterItem {
  public String urlFake;
  public String distance;
  public String price;
  public String title;
  public String desc;


  public ItemEventGoing() {
    this.urlFake = RandomGenerator.getUrlImage();
    this.distance = RandomGenerator.getDistance();
    this.price = RandomGenerator.getPrice();
    this.title = "Title";
    this.desc = "Desc";
  }

  @Override public int getViewType() {
    return 2;
  }
}
