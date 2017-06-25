package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderEventTop extends RecyclerView.ViewHolder implements DataViewHolder {

  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.image) public ImageView imageView;
  @BindView(R.id.btnBuy) public Button btnBuy;
  @BindView(R.id.btnNavigate) public Button btnNavigate;

  public ViewHolderEventTop(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    ItemEventTop eventTop = (ItemEventTop) adapterItem;
    if (eventTop != null && eventTop.event != null) {
      if (eventTop.event.title != null) {
        textViewTitle.setText(eventTop.event.title);
      }
      if (eventTop.event.description != null) {
        textViewDescription.setText(eventTop.event.description);
      }
      Picasso.with(imageView.getContext()).load(eventTop.urlFake).into(imageView);
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
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
