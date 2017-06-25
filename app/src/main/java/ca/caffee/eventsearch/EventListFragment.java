package ca.caffee.eventsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.calendar.Event;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
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
  private FastItemAdapter<EventItemRecycler> fastAdapter;

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
    fastAdapter = new FastItemAdapter();
    fastAdapter.withSelectable(true);
    fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<EventItemRecycler>() {
      @Override public boolean onClick(View v, IAdapter<EventItemRecycler> adapter, EventItemRecycler item, int position) {
        // Handle click here
        return true;
      }
    });
    recyclerView.setAdapter(fastAdapter);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(EventEventsRefreshed eventEventsRefreshed) {
    if (fastAdapter != null) {
      fastAdapter.clear();
    }
    for (Event event : eventEventsRefreshed.events) {
      if (fastAdapter != null) {
        fastAdapter.add(new EventItemRecycler().setEvent(event));
      }
    }
    if (fastAdapter != null) {
      fastAdapter.notifyAdapterDataSetChanged();
    }
  }
}
