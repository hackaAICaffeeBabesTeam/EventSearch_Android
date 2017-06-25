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
  @BindView(R.id.distance) public TextView textViewDistance;
  @BindView(R.id.price) public TextView textViewPrice;
  @BindView(R.id.image) public ImageView imageView;

  public ViewHolderEventSmall(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    ItemEventSmall event = (ItemEventSmall) adapterItem;
    if (event != null && event.event != null) {
      if (event.event.title != null) {
        textViewTitle.setText(event.event.title);
      }
      if (event.event.description != null) {
        textViewDescription.setText(event.event.description);
      }
      textViewDistance.setText(event.distance);
      textViewPrice.setText(event.price);
      Picasso.with(imageView.getContext()).load(event.urlFake).into(imageView);
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
