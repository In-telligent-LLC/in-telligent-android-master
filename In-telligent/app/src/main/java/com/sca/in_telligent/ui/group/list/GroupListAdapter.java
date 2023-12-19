package com.sca.in_telligent.ui.group.list;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.Building.Type;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.core.Observable;

public class GroupListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public List<Building> getBuildings() {
        return buildings;
    }

    private List<Building> buildings;
    private List<Building> suggestedBuildings;
    private Callback mCallback;
    private Context context;

    private boolean expanded = false;

    public static final int VIEW_TYPE_SUGGESTED_HEADER = 0;
    public static final int VIEW_TYPE_SUGGESTED_ITEM = 1;
    public static final int VIEW_TYPE_NORMAL = 2;
    public static final int GRAY_HEADER = 3;
    public static final int VIEW_TYPE_EMPTY = 4;

    public GroupListAdapter(List<Building> buildings, List<Building> suggestedBuildings,
                            Context context) {
        this.buildings = buildings;
        this.context = context;
        this.suggestedBuildings = suggestedBuildings;

    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void addItems(List<Building> listObjects, List<Building> suggestedListObjects) {
        buildings.clear();
        buildings.addAll(listObjects);
        suggestedBuildings.clear();
        suggestedBuildings.addAll(suggestedListObjects);
        notifyDataSetChanged();
        expand();
    }

    public void updateItems(List<Building> listObjects) {
        Observable.fromIterable(buildings).filter(
                building -> building.getType() != Type.NORMAL && building.getType() != Type.GRAY_HEADER)
                .toList().subscribe(buildingFiltered -> buildings = buildingFiltered);

        Observable.fromIterable(listObjects).filter(
                building -> building.getType() == Type.NORMAL || building.getType() == Type.GRAY_HEADER)
                .toList().subscribe(listObjectsFiltered -> buildings.addAll(listObjectsFiltered));
//    buildings.addAll(listObjects);
        notifyDataSetChanged();
    }

    public void updateSuggestedItems(List<Building> listObjects) {
        suggestedBuildings.clear();
        suggestedBuildings.addAll(listObjects);

        Observable.fromIterable(buildings).filter(building -> building.getType() != Type.SUGGESTED_ITEM)
                .toList().subscribe(buildingFiltered -> buildings = buildingFiltered);

        for (int i = 0; i < suggestedBuildings.size(); i++) {
            buildings.add(i + 1, suggestedBuildings.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
            default:
                return new GroupListAdapter.ViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_normal_list_item, parent, false));
            case VIEW_TYPE_SUGGESTED_HEADER:
                return new GroupListAdapter.SuggestedHeaderViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_suggested_header_item, parent, false));
            case VIEW_TYPE_SUGGESTED_ITEM:
                return new GroupListAdapter.SuggestedItemViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_suggested_list_item, parent, false));
            case GRAY_HEADER:
                return new GroupListAdapter.GrayViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.group_gray_header_item, parent, false));
            case VIEW_TYPE_EMPTY:
                return new GroupListAdapter.EmptyViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.group_list_empty_view, parent, false));


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
        if (buildings != null && buildings.size() > 1) {
            return buildings.get(position).getType().getNumber();
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public Building getItem(int position) {
        return buildings.get(position);
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.group_name)
        TextView groupName;

        @BindView(R.id.group_item_about_text)
        TextView itemAboutText;

        @BindView(R.id.group_normal_item_image)
        ImageView groupImage;

        @BindView(R.id.group_item_connect_text)
        TextView itemConnectText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            Building building = buildings.get(position);


            itemView.setOnClickListener(v -> {
                onAboutClick(building);
            });


            itemAboutText.setOnClickListener(v -> {
                onAboutClick(building);
            });

            itemConnectText.setOnClickListener(v -> {

                if (building.getSubscriberId() == null) {
                    if (building.isOther()) {
                        if (building.getPassword() != null) {
                            showPasswordDialog(building);
                        } else {
                            mCallback
                                    .onConnectClicked(Integer.toString(building.getId()),
                                            true, false);
                        }
                    } else {
                        mCallback
                                .onConnectClicked(Integer.toString(building.getId()),
                                        false, false);
                    }

                } else {
                    mCallback.onContactClicked(getAdapterPosition());
                }

            });

            groupName.setText(buildings.get(position).getName());
            if (building.getImageUrl() != null) {
                Glide.with(itemView)
                        .load(building.getImageUrl())
                        .thumbnail(0.1f)
                        .into(groupImage);
            } else {
                Glide.with(itemView).load(CommonUtils.getDefaultImage(building.getId()))
                        .thumbnail(0.1f)
                        .into(groupImage);
            }

            if (building.getSubscriberId() == null) {
                if (building.isOther()) {
                    itemConnectText.setText(context.getResources().getString(R.string.connect));
                } else {
                    itemConnectText.setText(context.getResources().getString(R.string.disconnect));
                }
            } else {
                itemConnectText.setText(context.getResources().getString(R.string.contact));
            }
        }

        private void onAboutClick(Building building) {
            int tmpPosition;
            if (expanded) {
                tmpPosition = getAdapterPosition() - suggestedBuildings.size();
            } else {
                tmpPosition = getAdapterPosition();
            }
            mCallback.onAboutClicked(tmpPosition,
                    building.getSubscriberId() != null);
        }
    }

    public class SuggestedHeaderViewHolder extends BaseViewHolder {

        @BindView(R.id.header_text)
        TextView headerText;

        @BindView(R.id.suggested_item_arrow)
        ImageView suggestedItemArrow;

        public SuggestedHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.suggested_groups_header_container)
        void onCollapseExpandSuggestedGroups() {
            if (expanded) {
                collapse();
                suggestedItemArrow.setImageResource(R.drawable.icon_down_carrot);
            } else {
                expand();
                suggestedItemArrow.setImageResource(R.drawable.icon_up_carrot);
            }
        }

        @Override
        protected void clear() {

        }

    }

    public class SuggestedItemViewHolder extends BaseViewHolder {

        @BindView(R.id.group_name)
        TextView groupName;

        @BindView(R.id.suggested_image)
        ImageView suggestedImage;

        @BindView(R.id.group_subscribe_text_layout)
        RelativeLayout suggestedSubscribeText;

        @BindView(R.id.group_ignore_text_layout)
        RelativeLayout suggestedIgnoreText;

        public SuggestedItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);
            Building building = buildings.get(position);

            suggestedSubscribeText.setOnClickListener(v -> {
                if (building.getPassword() != null) {
                    showPasswordDialog(buildings.get(getAdapterPosition()));
                } else {
                    mCallback
                            .onConnectClicked(Integer.toString(building.getId()), true, true);
                }
            });

            suggestedIgnoreText.setOnClickListener(v -> {
                int positionSubtractingHeader = position - 1;
                mCallback.onIgnoreClicked(building.getId() + "",
                        (positionSubtractingHeader >= 0) ? positionSubtractingHeader : 0);
            });


            groupName.setText(building.getName());
            if (building.getImageUrl() != null && !building.getImageUrl().equals("")) {
                Picasso.get().load(building.getImageUrl()).into(suggestedImage);
            } else {
                suggestedImage.setImageResource(CommonUtils.getDefaultImage(building.getId()));
            }
        }
    }


    public class GrayViewHolder extends BaseViewHolder {

        @BindView(R.id.gray_header_text)
        TextView headerText;

        public GrayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Building building = buildings.get(position);
            headerText.setText(building.getName());
        }
    }


    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.group_list_no_message_text)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }
    }

    public void expand() {
        for (int i = 0; i < suggestedBuildings.size(); i++) {
            buildings.add(i + 1, suggestedBuildings.get(i));
            notifyItemInserted(i + 1);
        }
        expanded = !expanded;
    }

    public void collapse() {
        for (int i = 0; i < suggestedBuildings.size(); i++) {
            buildings.remove(1);
            notifyItemRemoved(1);
        }
        expanded = !expanded;
    }

    private void showPasswordDialog(Building building) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(R.string.password);
        alertDialog.setMessage(
                context.getResources().getString(R.string.enter_password));

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(R.string.yes,
                (dialog, which) -> {
                    String password1 = input.getText().toString();

                    if (password1.equals(building.getPassword())) {
                        mCallback
                                .onConnectClicked(Integer.toString(building.getId()),
                                        true, false);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.invalid_password),
                                Toast.LENGTH_LONG).show();
                    }
                });
        alertDialog.setNegativeButton(R.string.no,
                (dialog, which) -> dialog.cancel());

        alertDialog.show();

    }

    public interface Callback {

        void onAboutClicked(int position, boolean createdByMe);

        void onContactClicked(int position);

        void onConnectClicked(String buildingId, boolean connect, boolean suggested);

        void onIgnoreClicked(String buildingId, int ignoredPosition);
    }
}
