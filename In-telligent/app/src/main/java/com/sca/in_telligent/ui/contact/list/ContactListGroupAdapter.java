package com.sca.in_telligent.ui.contact.list;

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
import butterknife.Unbinder;
import io.reactivex.rxjava3.annotations.NonNull;

public class ContactListGroupAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Building> buildings;
    private Callback mCallback;
    private Unbinder mUnBinder;


    public ContactListGroupAdapter(List<Building> buildings) {
        this.buildings = buildings;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactListGroupAdapter.ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.contact_list_group_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public void addItems(List<Building> listObjects) {
        buildings.addAll(listObjects);
        notifyDataSetChanged();
    }

    public void updateList(List<Building> list) {
        buildings = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.contact_group_item_message)
        ImageView messageButton;

        @BindView(R.id.contact_group_item_call)
        ImageView callButton;

        @BindView(R.id.contact_group_item_name)
        TextView groupName;

        @BindView(R.id.contact_group_item_profile_image)
        ImageView profileImage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }
        public void setUnBinder(Unbinder unBinder) {
            mUnBinder = unBinder;
        }

        public void onBind(final int position) {
            super.onBind(position);

            Building building = buildings.get(position);
            groupName.setText(building.getName());
            if (building.getImageUrl() != null && !building.getImageUrl().equals("")) {
                Picasso.get().load(building.getImageUrl()).into(profileImage);
            } else {
                profileImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
            }

            callButton.setVisibility(building.isVoipEnabled() ? View.VISIBLE : View.GONE);
            callButton.setOnClickListener(v -> mCallback.onCallSelected(building));


            messageButton.setVisibility((building.isTextEnabled() || building.isManagedByCurrentUser()) ? View.VISIBLE : View.GONE);
            messageButton.setOnClickListener(v -> mCallback.onMessageselected(building));
        }
    }

    public interface Callback {

        void onMessageselected(Building building);

        void onCallSelected(Building building);
    }
}
