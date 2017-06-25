package ca.caffee.eventsearch.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class EventListFragment extends Fragment {
  private static final String TAG = EventListFragment.class.getSimpleName();

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

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(EventEventsRefreshed eventEventsRefreshed) {
    ArrayList<AdapterItem> itemsToAdd = new ArrayList<>();
    if (eventEventsRefreshed.events.size() > 0) {
      for (Event event : eventEventsRefreshed.events) {
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
  }
}
