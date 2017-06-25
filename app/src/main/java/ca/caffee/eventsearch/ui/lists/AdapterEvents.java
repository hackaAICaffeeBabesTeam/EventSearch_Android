package ca.caffee.eventsearch.ui.lists;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.calendar.Event;
import java.util.ArrayList;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolderEvent> {
  public ArrayList<Event> events = new ArrayList<>();
  private Activity context;

  public AdapterEvents(Activity context) {
    this.context = context;
  }

  @Override public ViewHolderEvent onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_small, parent, false);
    return new ViewHolderEvent(itemView);
  }

  @Override public void onBindViewHolder(final ViewHolderEvent holder, int position) {
    final Event event = events.get(position);
    holder.title.setText(event.title);
  }

  @Override public int getItemCount() {
    return events.size();
  }

  public class ViewHolderEvent extends RecyclerView.ViewHolder {

    @BindView(R.id.title) public TextView title;

    public ViewHolderEvent(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
