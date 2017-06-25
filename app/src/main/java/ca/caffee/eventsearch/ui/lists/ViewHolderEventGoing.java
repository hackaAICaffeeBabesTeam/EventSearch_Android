package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class ViewHolderEventGoing extends RecyclerView.ViewHolder implements DataViewHolder {

  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.image) public ImageView imageView;
  @BindView(R.id.price) public TextView textViewPrice;
  @BindView(R.id.distance) public TextView textViewDistance;
  @BindView(R.id.btnNavigate) public Button btnNavigate;
  @BindView(R.id.mainRelative) public RelativeLayout relativeLayout;

  public ViewHolderEventGoing(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    final ItemEventGoing event = (ItemEventGoing) adapterItem;
    if (event != null) {
      if (event.title != null) {
        textViewTitle.setText(event.title);
      }
      if (event.desc != null) {
        textViewDescription.setText(event.desc);
      }
      textViewDistance.setText(event.distance);
      textViewPrice.setText(event.price);
      Picasso.with(imageView.getContext()).load(event.urlFake).into(imageView);
      btnNavigate.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=New+York+NY"));
          btnNavigate.getContext().startActivity(intent);
        }
      });
      relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(relativeLayout.getContext(), EventActivity.class);
          intent.putExtra("eventGoing", Parcels.wrap(event));
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
