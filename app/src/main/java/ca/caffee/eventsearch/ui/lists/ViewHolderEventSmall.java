package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderEventSmall extends RecyclerView.ViewHolder implements DataViewHolder {
  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.image) public ImageView imageView;

  public ViewHolderEventSmall(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    ItemEventSmall eventSmall = (ItemEventSmall) adapterItem;
    if (eventSmall != null && eventSmall.event != null) {
      if (eventSmall.event.title != null) {
        textViewTitle.setText(eventSmall.event.title);
      }
      if (eventSmall.event.description != null) {
        textViewDescription.setText(eventSmall.event.description);
      }
      Picasso.with(imageView.getContext()).load(eventSmall.urlFake).into(imageView);
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
