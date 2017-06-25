package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.caffee.eventsearch.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public ArrayList<AdapterItem> adapterItems = new ArrayList<>();
  private WeakReference<Context> context;
  public boolean isSorted;

  public AdapterMain(Context context) {
    this.context = new WeakReference<Context>(context);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    switch (viewType) {
      case 0:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_top, parent, false);
        return new ViewHolderEventTop(itemView);
      case 1:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_small, parent, false);
        return new ViewHolderEventSmall(itemView);
      default:
        break;
    }
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (holder.getItemViewType()) {
      case 0:
        ViewHolderEventTop viewHolderEventTop = (ViewHolderEventTop) holder;
        viewHolderEventTop.setData(position, adapterItems.get(position), context.get());
        break;
      case 1:
        ViewHolderEventSmall viewHolderEventSmall = (ViewHolderEventSmall) holder;
        viewHolderEventSmall.setData(position, adapterItems.get(position), context.get());
        break;
      default:
        break;
    }
  }

  public void removeEvent(Long id) {
    if (id == null) {
      return;
    }
    for (AdapterItem adapterItem : new ArrayList<AdapterItem>(adapterItems)) {
      if (adapterItem instanceof ItemEventTop) {
        //TODO
      }
    }
  }

  @Override public int getItemCount() {
    return adapterItems.size();
  }

  @Override public int getItemViewType(int position) {
    return adapterItems.get(position).getViewType();
  }
}
