package ca.caffee.eventsearch.ui.lists;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mtajc on 25.06.2017.
 */

public class RandomImageGenerator {
  static ArrayList<String> urls = new ArrayList<String>() {{
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event.jpg?alt=media&token=b6ba8cb8-45e0-43db-a62d-3cf774c79d3c");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_1.jpg?alt=media&token=e8b96039-3a50-4756-a063-23f7fcca966f");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_11.jpg?alt=media&token=221b3026-57b2-445d-83d1-3e0ab80b9b53");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_12.jpg?alt=media&token=a62dd796-94b9-48ae-93e6-8e5049614725");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_13.jpg?alt=media&token=7f67ba12-d1bd-4b6a-b690-0f0800b5bf43");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_14.jpg?alt=media&token=7afdbde6-afb0-4c2d-8cef-26396d165530");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_15.jpg?alt=media&token=9bb3d138-c041-49e8-8bbb-3ab2a3a2ccec");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_2.jpg?alt=media&token=647f4ce4-3a78-4768-b87c-a50a0adc0164");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_3.jpg?alt=media&token=f5bc64f8-b7bd-49db-be4b-540f0e3ea864");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_4.jpg?alt=media&token=0e38d35d-277e-4ac7-8b7a-67d7b87fe3b3");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_6.jpg?alt=media&token=849268b2-9c18-4233-acfa-61e2564cfd66");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_7.jpg?alt=media&token=e31ef0a4-2e70-47d3-b178-f68800ba7338");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_8.jpg?alt=media&token=38b8c6c1-eeda-4f89-944b-c250877605e6");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/event_9.jpg?alt=media&token=96a3a708-9783-4215-b8b1-a9d9cb3f08f9");
    add("https://firebasestorage.googleapis.com/v0/b/hackai-eventsearch.appspot.com/o/events_5.jpg?alt=media&token=5b7493ce-8555-4a5c-98f0-1995dd79a42f");
  }};

  public static String getUrlImage() {
    Random rand = new Random();
    int randomNum = rand.nextInt(urls.size());
    return urls.get(randomNum);
  }
}
