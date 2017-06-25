package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import ca.caffee.eventsearch.ui.EventActivity;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderEventSmall extends RecyclerView.ViewHolder implements DataViewHolder {
  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.distance) public TextView textViewDistance;
  @BindView(R.id.price) public TextView textViewPrice;
  @BindView(R.id.image) public ImageView imageView;
  @BindView(R.id.mainRelative) public RelativeLayout relativeLayout;

  public ViewHolderEventSmall(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    final ItemEventSmall event = (ItemEventSmall) adapterItem;
    if (event != null && event.event != null) {
      if (event.event.title != null) {
        textViewTitle.setText(event.event.title);
      }
      if (event.event.description != null) {
        textViewDescription.setText(event.event.description);
      }
      if (event.event.url != null) {
        Picasso.with(imageView.getContext()).load(event.event.url).into(imageView);
      } else {
        Picasso.with(imageView.getContext()).load(event.urlFake).into(imageView);
      }
      if (event.event.distance != null) {
        textViewDistance.setText(event.event.distance);
      } else {
        textViewDistance.setText(event.distance);
      }
      if (event.event.price != null) {
        textViewPrice.setText(event.price);
      } else {
        textViewPrice.setText(event.price);
      }
      relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(relativeLayout.getContext(), EventActivity.class);
          intent.putExtra("eventSmall", Parcels.wrap(event));
          relativeLayout.getContext().startActivity(intent);
        }
      });
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
