package pl.tajchert.canary.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import pl.tajchert.canary.R;
import pl.tajchert.canary.data.FirebaseStation;
import pl.tajchert.canary.ui.adapteritems.AdapterItem;
import pl.tajchert.canary.ui.adapteritems.CardFacebookItemRecycler;
import pl.tajchert.canary.ui.adapteritems.CardRateAppItemRecycler;
import pl.tajchert.canary.ui.adapteritems.StationItemRecycler;
import pl.tajchert.canary.ui.adapteritems.ViewHolderCardEmpty;
import pl.tajchert.canary.ui.adapteritems.ViewHolderCardFacebook;
import pl.tajchert.canary.ui.adapteritems.ViewHolderCardRateApp;
import pl.tajchert.canary.ui.adapteritems.ViewHolderStationCompact;
import pl.tajchert.canary.ui.adapteritems.ViewHolderText;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public ArrayList<AdapterItem> adapterItems = new ArrayList<>();
  private WeakReference<Context> context;
  public boolean isSorted;

  public AdapterMain(Context context) {
    this.context = new WeakReference<Context>(context);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    switch (viewType) {
      case 0:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ViewHolderText(itemView);
      case 1:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station_read_compact, parent, false);
        return new ViewHolderStationCompact(itemView);
      case 2:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_empty, parent, false);
        return new ViewHolderCardEmpty(itemView);
      case 3:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_facebook, parent, false);
        return new ViewHolderCardFacebook(itemView);
      case 4:
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_rateapp, parent, false);
        return new ViewHolderCardRateApp(itemView);
      default:
        break;
    }
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (holder.getItemViewType()) {
      case 0:
        ViewHolderText viewHolderText = (ViewHolderText) holder;
        viewHolderText.setData(position, adapterItems.get(position), context.get());
        break;
      case 1:
        ViewHolderStationCompact viewHolderStation = (ViewHolderStationCompact) holder;
        viewHolderStation.setData(position, adapterItems.get(position), context.get());
        break;
      case 2:
        ViewHolderCardEmpty viewHolderCardEmpty = (ViewHolderCardEmpty) holder;
        viewHolderCardEmpty.setData(position, adapterItems.get(position), context.get());
        break;
      case 3:
        ViewHolderCardFacebook viewHolderCardFacebook = (ViewHolderCardFacebook) holder;
        viewHolderCardFacebook.setData(position, adapterItems.get(position), context.get());
        break;
      case 4:
        ViewHolderCardRateApp viewHolderCardRateApp = (ViewHolderCardRateApp) holder;
        viewHolderCardRateApp.setData(position, adapterItems.get(position), context.get());
        break;
      default:
        break;
    }
  }

  public ArrayList<FirebaseStation> getFavourites() {
    ArrayList<FirebaseStation> firebaseStations = new ArrayList<>();
    for (AdapterItem adapterItem : adapterItems) {
      if (adapterItem instanceof StationItemRecycler && ((StationItemRecycler) adapterItem).firebaseStation != null) {
        if (((StationItemRecycler) adapterItem).firebaseStation.isFavourite) {
          firebaseStations.add(((StationItemRecycler) adapterItem).firebaseStation);
        }
      }
    }
    return firebaseStations;
  }

  public void removeNearestStation() {
    for (AdapterItem adapterItem : new ArrayList<AdapterItem>(adapterItems)) {
      if (adapterItem instanceof StationItemRecycler && ((StationItemRecycler) adapterItem).firebaseStation != null) {
        if (((StationItemRecycler) adapterItem).isNearest) {
          adapterItems.remove(adapterItem);
        }
      }
    }
  }

  public void removeFacebookCard() {
    for (AdapterItem adapterItem : new ArrayList<AdapterItem>(adapterItems)) {
      if (adapterItem instanceof CardFacebookItemRecycler) {
        adapterItems.remove(adapterItem);
      }
    }
  }

  public void removeRateCard() {
    for (AdapterItem adapterItem : new ArrayList<AdapterItem>(adapterItems)) {
      if (adapterItem instanceof CardRateAppItemRecycler) {
        adapterItems.remove(adapterItem);
      }
    }
  }

  public void removeStation(Long id) {
    if (id == null) {
      return;
    }
    for (AdapterItem adapterItem : new ArrayList<AdapterItem>(adapterItems)) {
      if (adapterItem instanceof StationItemRecycler && ((StationItemRecycler) adapterItem).firebaseStation != null) {
        if (((StationItemRecycler) adapterItem).firebaseStation.id != null && ((StationItemRecycler) adapterItem).firebaseStation.id == id) {
          adapterItems.remove(adapterItem);
        }
      }
    }
  }

  @Override public int getItemCount() {
    return adapterItems.size();
  }

  @Override public int getItemViewType(int position) {
    return adapterItems.get(position).getViewType();
  }
}
