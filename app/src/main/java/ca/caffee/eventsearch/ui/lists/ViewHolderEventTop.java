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

public class ViewHolderEventTop extends RecyclerView.ViewHolder implements DataViewHolder {

  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.image) public ImageView imageView;
  @BindView(R.id.price) public TextView textViewPrice;
  @BindView(R.id.distance) public TextView textViewDistance;
  @BindView(R.id.btnBuy) public Button btnBuy;
  @BindView(R.id.btnNavigate) public Button btnNavigate;
  @BindView(R.id.mainRelative) public RelativeLayout relativeLayout;

  public ViewHolderEventTop(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    final ItemEventTop event = (ItemEventTop) adapterItem;
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
      btnNavigate.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=New+York+NY"));
          btnNavigate.getContext().startActivity(intent);
        }
      });
      btnBuy.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          try {
            Intent intent = btnBuy.getContext().getPackageManager().getLaunchIntentForPackage("com.stubhub");
            btnBuy.getContext().startActivity(intent);
          } catch (Exception e) {
            //No stubhub app
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.stubhub.com/toronto-fc-tickets-toronto-fc-toronto-bmo-field-6-27-2017/event/103008565/"));
            btnBuy.getContext().startActivity(intent);
          }
        }
      });
      relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Intent intent = new Intent(relativeLayout.getContext(), EventActivity.class);
          intent.putExtra("eventTop", Parcels.wrap(event));
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
