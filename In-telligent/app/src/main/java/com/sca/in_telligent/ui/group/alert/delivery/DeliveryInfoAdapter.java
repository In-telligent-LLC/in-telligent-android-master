package com.sca.in_telligent.ui.group.alert.delivery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoItem;
import com.sca.in_telligent.ui.base.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryInfoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  private static final String TAG = DeliveryInfoAdapter.class.getSimpleName();

  private ArrayList<DeliveryInfoItem> deliveryInfoItems;

  public DeliveryInfoAdapter(ArrayList<DeliveryInfoItem> deliveryInfoItems) {
    this.deliveryInfoItems = deliveryInfoItems;
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new DeliveryInfoAdapter.ViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.delivery_info_item_view, parent, false));

  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemCount() {
    return deliveryInfoItems.size();
  }

  public void addItems(ArrayList<DeliveryInfoItem> listObjects) {
    deliveryInfoItems.addAll(listObjects);
    notifyDataSetChanged();
  }

  public class ViewHolder extends BaseViewHolder {

    @BindView(R.id.delivery_info_item_name)
    TextView nameText;

    @BindView(R.id.delivery_info_item_received)
    ImageView receivedImage;

    @BindView(R.id.delivery_info_item_viewed)
    ImageView viewedImage;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void onBind(final int position) {
      super.onBind(position);

      DeliveryInfoItem infoItem = deliveryInfoItems.get(position);

      if(infoItem.getDeliverySubscriber() == null){
        Log.e(TAG, "onBind: infoItem.getDeliverySubscriber() == null");
        return;
      }

      nameText.setText(infoItem.getDeliverySubscriber().getName());

      receivedImage.setImageResource(
          infoItem.getDelivered() != null ? R.drawable.checkmark : R.drawable.cross_mark);

      viewedImage.setImageResource(
          infoItem.getOpened() != null ? R.drawable.checkmark : R.drawable.cross_mark);

    }
  }
}
