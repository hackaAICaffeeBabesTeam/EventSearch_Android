package ca.caffee.eventsearch.ui.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.caffee.eventsearch.R;

/**
 * Created by mtajc on 25.06.2017.
 */

public class ViewHolderNotification extends RecyclerView.ViewHolder implements DataViewHolder {

  @BindView(R.id.description) public TextView textViewDescription;
  @BindView(R.id.title) public TextView textViewTitle;
  @BindView(R.id.image) public ImageView imageView;
  @BindView(R.id.mainRelative) public RelativeLayout relativeLayout;

  public ViewHolderNotification(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override public void setData(int position, AdapterItem adapterItem, Context context) {
    final ItemNotification itemNotification = (ItemNotification) adapterItem;
    if (itemNotification != null) {
      if (itemNotification.title != null) {
        textViewTitle.setText(itemNotification.title);
      }
      if (itemNotification.desc != null) {
        textViewDescription.setText(itemNotification.desc);
      }
      //Picasso.with(imageView.getContext()).load(itemNotification.urlFake).into(imageView);
      relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          //Intent intent = new Intent(relativeLayout.getContext(), EventActivity.class);
          //intent.putExtra("eventGoing", Parcels.wrap(itemNotification));
          //relativeLayout.getContext().startActivity(intent);
        }
      });
    }
  }

  @Override public void onViewDetached(View view) {

  }

  @Override public void onViewAttached(View view) {

  }
}
