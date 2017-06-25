package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderEventTop extends RecyclerView.ViewHolder implements DataViewHolder {
  @BindView(R.id.title) public TextView textViewTitle;

  public ViewHolderEventTop(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    ItemEventTop eventTop = (ItemEventTop) adapterItem;
    if (eventTop != null) {
      if (eventTop.event != null && eventTop.event.title != null) {
        textViewTitle.setText(eventTop.event.title);
      }
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
