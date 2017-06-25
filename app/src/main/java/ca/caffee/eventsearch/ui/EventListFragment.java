package ca.caffee.eventsearch.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.calendar.Event;
import ca.caffee.eventsearch.events.EventEventsRefreshed;
import ca.caffee.eventsearch.ui.lists.AdapterItem;
import ca.caffee.eventsearch.ui.lists.AdapterMain;
import ca.caffee.eventsearch.ui.lists.ItemEventSmall;
import ca.caffee.eventsearch.ui.lists.ItemEventTop;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
  private static final String TAG = EventListFragment.class.getSimpleName();

  @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private ArrayList<Event> events = new ArrayList<>();
  private AdapterMain adapterEvents;

  public static EventListFragment newInstance() {
    EventListFragment myFragment = new EventListFragment();
    Bundle args = new Bundle();
    myFragment.setArguments(args);
    return myFragment;
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_event_list, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setRecyclerView();
    swipeRefreshLayout.setOnRefreshListener(this);
  }

  private void setRecyclerView() {
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        return false;
      }

      @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if (adapterEvents != null) {
          adapterEvents.adapterItems.remove(viewHolder.getAdapterPosition());
          adapterEvents.notifyItemRangeRemoved(viewHolder.getAdapterPosition(), 1);
          Toast.makeText(getActivity(), "Event was removed", Toast.LENGTH_SHORT).show();
        }
      }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapterEvents = new AdapterMain(getActivity());
    recyclerView.setAdapter(adapterEvents);
    recyclerView.setHasFixedSize(true);
  }

  private void setDummyData() {
    events.clear();
    events.add(new Event(
        "For 14 years XLIVE has been providing resources and information to the live event and eSports industries. The XLIVE eSports Summit is the first senior level forum of its kind that will bring together eSports industry stakeholders and traditional professional sports franchises under one roof to discuss the impact that eSports will have on the future of the sports and entertainment industries. This event will convene eSports team owners/players, venue operators, tournament organizers, broadcasters, sponsors, agencies, legal experts, consultants, product developers, venture capitalists with professional sports teams investing in eSports for an intimate event focused on the business and technological evolution of the rapidly evolving eSports market.",
        "XLIVE Esports Summit",
        "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F27743491%2F182267628368%2F1%2Foriginal.jpg?w=800&rect=0%2C408%2C3264%2C1632&s=ba1dbc30381cd4d07e76837cb5483264",
        "790 km", "$795", "139 West 26th Street\n" + "New York", "https://www.eventbrite.com/e/xlive-esports-summit-tickets-31485782824", null));
    events.add(new Event(
        "Calling all High School and college students! Come in with your School ID and get an All day pass with 2 slices of pizza and a drink every Wednesday for $15. Must have a current School ID.",
        "eSports Student Night",
        "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F31602070%2F161136221763%2F1%2Foriginal.jpg?w=800&rect=71%2C0%2C478%2C239&s=2ff070caaaa55cd8fc346529ae82aec4",
        "642km", "Free", "10 Cross Street\n" + "Norwalk", "https://www.eventbrite.com/e/esports-student-night-tickets-34759010135", null));
    events.add(new Event(
        "Each participating LAN center will host high school teams at their location for these one day bracketed tournaments. Our goal is to have 20-30 participating centers each hosting 3-6 high school teams. These will be the single largest events held for high school esports in physical locations around the United States.",
        "Overwatch High School League Evo Gaming NYC",
        "https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F32311671%2F175102411505%2F1%2Foriginal.jpg?w=800&rect=0%2C0%2C1920%2C960&s=3bef10c283cd2ca39c7f8639d2c4d0d1",
        "765km", "$90", "Roosevelt Ave\n" + "Basement\n" + "Flushing, NY",
        "https://www.eventbrite.com/e/overwatch-high-school-league-evo-gaming-nyc-tickets-35360720868", null));
    events.add(new Event(
        "NY Games Conference is a 1-day event that is part of New York Media Festival’s (NYME) 4-day event series. The 1-day conference is the leading event for senior representatives from game publishers, developers, movie studios, advertising firms, VCs, social networks, technology providers, analysts and press. Unlike some other industry events, NY Games Conference focuses on bringing together the people who really matter to meet in a lively yet intimate environment that allows access and privacy to build and grow relationships and partnerships. Year after year, attendees tell me about the importance of NYGC for the ROI it provides. It’s not just the deals they make but people they meet and the long lasting relationships forged. If you are serious about the business of games and interactive entertainment, we invite you to join us at NY Games Conference.",
        "NY Games Conference @ NYME 2017",
        "https://s.evbuc.com/https_proxy?url=http%3A%2F%2Fwww.digitalmediawire.net%2F2017email%2Fcb%2FNYME%2FIMAGES%2Fheader-games.png&sig=ADR2i7-jKi8rZMH9SdKZDzdbbqpDhegIug",
        "799 km", "$ 499", "36 Battery Place \n" + "New York", "https://www.eventbrite.com/e/ny-games-conference-nyme-2017-tickets-34641917909",
        null));
    if (events != null && events.size() > 0) {
      setEvents(events);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(EventEventsRefreshed eventEventsRefreshed) {
    if (eventEventsRefreshed.events.size() > 0) {
      this.events = eventEventsRefreshed.events;
      //setEvents(this.events);
    }
  }

  private void setEvents(ArrayList<Event> events) {
    ArrayList<AdapterItem> itemsToAdd = new ArrayList<>();
    for (Event event : events) {
      if (itemsToAdd.size() == 0) {
        itemsToAdd.add(new ItemEventTop(event));
      } else {
        itemsToAdd.add(new ItemEventSmall(event));
      }
    }
    if (adapterEvents != null) {
      adapterEvents.adapterItems.clear();
      adapterEvents.adapterItems.addAll(itemsToAdd);
      adapterEvents.notifyDataSetChanged();
    }
  }

  @Override public void onRefresh() {
    if (events.size() > 0) {
      events.clear();
      adapterEvents.adapterItems.clear();
      adapterEvents.notifyDataSetChanged();
      swipeRefreshLayout.setRefreshing(false);
    } else {
      Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        public void run() {
          if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
          }
          setDummyData();
        }
      }, 2000);
    }
  }
}
