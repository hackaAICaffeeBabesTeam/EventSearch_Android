package ca.caffee.eventsearch;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.calendar.Event;
import java.util.ArrayList;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolderStation> {
  public ArrayList<Event> events = new ArrayList<>();
  private Activity context;

  public AdapterEvents(Activity context) {
    this.context = context;
  }

  @Override public ViewHolderStation onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
    return new ViewHolderStation(itemView);
  }

  @Override public void onBindViewHolder(final ViewHolderStation holder, int position) {
    final Event event = events.get(position);
    holder.title.setText(event.title);
  }

  @Override public int getItemCount() {
    return events.size();
  }

  public class ViewHolderStation extends RecyclerView.ViewHolder {

    @BindView(R.id.title) public TextView title;

    public ViewHolderStation(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
