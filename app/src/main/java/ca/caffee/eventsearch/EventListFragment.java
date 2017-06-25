package ca.caffee.eventsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.calendar.Event;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private ArrayList<Event> events = new ArrayList<>();
  private AdapterEvents adapterEvents;

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
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapterEvents = new AdapterEvents(getActivity());
    recyclerView.setAdapter(adapterEvents);
    recyclerView.setHasFixedSize(true);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(EventEventsRefreshed eventEventsRefreshed) {
    if (adapterEvents != null && eventEventsRefreshed.events.size() > 0) {
      adapterEvents.events.clear();
      adapterEvents.events.addAll(eventEventsRefreshed.events);
      adapterEvents.notifyDataSetChanged();
    }
  }
}
