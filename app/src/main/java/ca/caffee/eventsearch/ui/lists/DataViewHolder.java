package pl.tajchert.canary.ui;

import android.content.Context;
import android.view.View;
import pl.tajchert.canary.ui.adapteritems.AdapterItem;

public interface DataViewHolder {
  public void setData(int position, AdapterItem adapterItem, Context context);

  public void onViewDetached(View view);

  public void onViewAttached(View view);
}
