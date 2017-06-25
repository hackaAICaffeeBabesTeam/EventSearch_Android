package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.caffee.eventsearch.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdapterGoing extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public ArrayList<AdapterItem> adapterItems = new ArrayList<>();
  private WeakReference<Context> context;
  public boolean isSorted;

  public AdapterGoing(Context context) {
    this.context = new WeakReference<Context>(context);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    switch (viewType) {
      case 2:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_going, parent, false);
        return new ViewHolderEventGoing(itemView);
      default:
        break;
    }
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (holder.getItemViewType()) {
      case 2:
        ViewHolderEventGoing viewHolderEventGoing = (ViewHolderEventGoing) holder;
        viewHolderEventGoing.setData(position, adapterItems.get(position), context.get());
        break;
      default:
        break;
    }
  }

  @Override public int getItemCount() {
    return adapterItems.size();
  }

  @Override public int getItemViewType(int position) {
    return adapterItems.get(position).getViewType();
  }
}
