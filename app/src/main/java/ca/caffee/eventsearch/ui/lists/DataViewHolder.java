package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.view.View;

public interface DataViewHolder {
  public void setData(int position, AdapterItem adapterItem, Context context);

  public void onViewDetached(View view);

  public void onViewAttached(View view);
}
