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
import ca.caffee.eventsearch.ui.lists.AdapterNotification;
import ca.caffee.eventsearch.ui.lists.ItemNotification;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {
  private static final String TAG = NotificationsFragment.class.getSimpleName();

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private ArrayList<Event> events = new ArrayList<>();
  private AdapterNotification adapterNotification;

  public static NotificationsFragment newInstance() {
    NotificationsFragment myFragment = new NotificationsFragment();
    Bundle args = new Bundle();
    myFragment.setArguments(args);
    return myFragment;
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
        if (adapterNotification != null) {
          adapterNotification.adapterItems.remove(viewHolder.getAdapterPosition());
          adapterNotification.notifyItemRangeRemoved(viewHolder.getAdapterPosition(), 1);
          Toast.makeText(getActivity(), "Event was removed", Toast.LENGTH_SHORT).show();
        }
      }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapterNotification = new AdapterNotification(getActivity());
    recyclerView.setAdapter(adapterNotification);
    recyclerView.setHasFixedSize(true);

    adapterNotification.adapterItems.clear();
    for (int i = 0; i < 30; i++) {
      adapterNotification.adapterItems.add(new ItemNotification());
    }
    adapterNotification.notifyDataSetChanged();
  }
}
