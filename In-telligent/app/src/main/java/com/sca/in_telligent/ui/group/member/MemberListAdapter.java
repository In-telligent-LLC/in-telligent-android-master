package com.sca.in_telligent.ui.group.member;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Invitee> inviteeList = new ArrayList<>();
    private Callback mCallback;

    public MemberListAdapter() {

    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MemberListAdapter.ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.member_list_item_view, parent, false));

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return inviteeList.size();
    }

    @Override
    public long getItemId(int position) {
        return inviteeList.get(position).getId();
    }

    public void addItems(List<Invitee> listObjects) {
        inviteeList.clear();
        inviteeList.addAll(listObjects);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        inviteeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, inviteeList.size());
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.member_item_name_text)
        TextView memberName;

        @BindView(R.id.member_item_check_image)
        ImageView checkImage;

        @BindView(R.id.member_item_close_image)
        ImageView closeImage;

        @BindView(R.id.member_item_location_image)
        ImageView locationImage;

        @OnClick(R.id.member_item_check_image)
        void editMember(View v) {
            mCallback.onEditSelected(getAdapterPosition());
        }

        @OnClick(R.id.member_item_location_image)
        void memberLocation(View v) {
            String name = inviteeList.get(getAdapterPosition()).getName();
            if (name == null) {
                name = "";
            }
            mCallback
                    .onMemberLocation(getAdapterPosition(), name);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            Invitee invitee = inviteeList.get(position);

            closeImage.setOnClickListener(view -> mCallback.onDeleteSelected(invitee.getId(),
                    memberName.getText().toString(),
                    position));

            boolean isAccepted = invitee.getStatus().equals("accepted");
            if (!isAccepted) {
                locationImage.setVisibility(View.GONE);
                checkImage.setVisibility(View.GONE);
            } else {
                locationImage.setVisibility(View.VISIBLE);
                checkImage.setVisibility(View.VISIBLE);
            }

            if (invitee.getName() != null && !invitee.getName().isEmpty()) {
                memberName.setText(invitee.getName());
            } else {
                memberName.setText(invitee.getEmail());
            }

            String pendingString = itemView.getContext().getString(R.string.pending).toLowerCase();

            if (!isAccepted) {
                memberName.append(" (" + pendingString + ")");
                memberName.setAlpha(0.7f);
            } else {
                memberName.setAlpha(1f);
            }
        }
    }

    public interface Callback {

        void onDeleteSelected(int inviteId, String memberName, int position);

        void onEditSelected(int position);

        void onMemberLocation(int position, String name);
    }
}
