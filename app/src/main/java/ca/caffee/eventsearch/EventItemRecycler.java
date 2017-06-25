package ca.caffee.eventsearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.calendar.Event;
import com.mikepenz.fastadapter.items.AbstractItem;
import java.util.List;

/**
 * Created by mtajc on 24.06.2017.
 */

public class EventItemRecycler extends AbstractItem<EventItemRecycler, EventItemRecycler.ViewHolder> {
  private Event event;
  private int id = 1;

  @Override public ViewHolder getViewHolder(View v) {
    return new ViewHolder(v);
  }

  @Override public int getType() {
    return id;
  }

  @Override public int getLayoutRes() {
    return R.layout.item_event;
  }

  public EventItemRecycler setEvent(Event event) {
    this.event = event;
    return this;
  }

  @Override public void bindView(ViewHolder viewHolder, List payloads) {
    super.bindView(viewHolder, payloads);
    viewHolder.title.setText(event.title);
  }

  protected static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title) TextView title;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
