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
    this.title = "Event going ";
    this.desc = "Festival weekend’s premier event is the Pride Parade. With 150+ participating groups marching, the Toronto Pride parade has become one of the largest in North America. There will be unforgettable performances, floats, marchers, and all the glitter you can handle!";
  }

  @Override public int getViewType() {
    return 2;
  }
}
