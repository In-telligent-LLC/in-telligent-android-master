package com.sca.in_telligent.ui.settings.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationSettingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  public static final int VIEW_TYPE_EMPTY = 0;
  public static final int VIEW_TYPE_NORMAL = 1;

  private final List<Building> buildings;
  private Callback mCallback;

  public NotificationSettingAdapter(List<Building> buildings) {
    this.buildings = buildings;
  }

  public void setCallback(Callback callback) {
    mCallback = callback;
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    switch (viewType) {
      case VIEW_TYPE_NORMAL:
        return new NotificationSettingAdapter.ViewHolder(
            LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.notification_settings_list_item, parent, false));
      case VIEW_TYPE_EMPTY:
      default:
        return new NotificationSettingAdapter.EmptyViewHolder(
            LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.notification_settings_empty_item, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemCount() {
    if (buildings != null && buildings.size() > 1) {
      return buildings.size();
    } else {
      return 1;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (buildings != null && buildings.size() > 0) {
      return VIEW_TYPE_NORMAL;
    } else {
      return VIEW_TYPE_EMPTY;
    }
  }

  public void addItems(List<Building> listObjects) {
    buildings.clear();
    buildings.addAll(listObjects);
    notifyDataSetChanged();
  }

  public class ViewHolder extends BaseViewHolder {

    @BindView(R.id.notification_setting_item_image)
    ImageView itemImage;

    @BindView(R.id.notification_setting_title_text)
    TextView titleText;

    @BindView(R.id.notification_setting_always_text)
    TextView buttonAlways;

    @BindView(R.id.notification_setting_only_text)
    TextView buttonOnly;

    @BindView(R.id.notification_setting_never_text)
    TextView buttonNever;

    @OnClick({R.id.notification_setting_never_text, R.id.notification_setting_always_text,
        R.id.notification_setting_only_text})
    void syncClick(View v) {
      String subscription = "";
      if (v == buttonAlways) {
        subscription = "a";
      } else if (v == buttonOnly) {
        subscription = "i";
      } else {
        subscription = "n";
      }
      selectButton(subscription);
      mCallback
          .onItemSelected(Integer.toString(buildings.get(getAdapterPosition()).getId()),
              subscription);
    }

    public ViewHolder(View itemView) {
      super(itemView);
      itemImage = itemView.findViewById(R.id.notification_setting_item_image);
        titleText = itemView.findViewById(R.id.notification_setting_title_text);
        buttonAlways = itemView.findViewById(R.id.notification_setting_always_text);
        buttonOnly = itemView.findViewById(R.id.notification_setting_only_text);
        buttonNever = itemView.findViewById(R.id.notification_setting_never_text);

      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void onBind(final int position) {
      super.onBind(position);
      Building building = buildings.get(position);
      titleText.setText(building.getName());

      if (building.getBuildingAddress().isVirtual()) {
        buttonOnly.setVisibility(View.GONE);
      }
      else
      {
        buttonOnly.setVisibility(View.VISIBLE);
      }

      selectButton(building.getBuildingsSubscriber().getAlertsSubscription());

      if (building.getImageUrl() != null) {
        Picasso.get().load(building.getImageUrl())
            .into(itemImage);
      } else {
        itemImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
      }

    }

    private void selectButton(String alertSubscriptionString) {
      if (alertSubscriptionString.equals("a")) {
        buttonAlways.setSelected(true);
        buttonOnly.setSelected(false);
        buttonNever.setSelected(false);
      } else if (alertSubscriptionString.equals("i")) {
        buttonOnly.setSelected(true);
        buttonAlways.setSelected(false);
        buttonNever.setSelected(false);
      } else {
        buttonNever.setSelected(true);
        buttonAlways.setSelected(false);
        buttonOnly.setSelected(false);
      }
    }
  }

  public class EmptyViewHolder extends BaseViewHolder {

    public EmptyViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }
  }

  public interface Callback {

    void onItemSelected(String buildingId, String subscription);
  }

}
