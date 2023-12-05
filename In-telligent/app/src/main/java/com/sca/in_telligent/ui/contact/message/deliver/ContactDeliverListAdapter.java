package com.sca.in_telligent.ui.contact.message.deliver;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class ContactDeliverListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Invitee> invitees;
    private List<BuildingMember> buildingMembers;
    private ArrayList<Invitee> checkedItemIds = new ArrayList<>();
    private ArrayList<BuildingMember> checkedBuildingMembers = new ArrayList<>();
    private Callback mCallback;

    public static final int VIEW_TYPE_INVITEE = 1;
    public static final int VIEW_TYPE_MEMBER = 2;

    public ContactDeliverListAdapter(List<Invitee> invitees, List<BuildingMember> buildingMembers) {
        this.invitees = invitees;
        this.buildingMembers = buildingMembers;
    }

    public void setCallback(ContactDeliverListAdapter.Callback callback) {
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_INVITEE:
                return new ContactDeliverListAdapter.ViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.contact_deliver_invitee_item, parent, false));
            default:
                return new ContactDeliverListAdapter.MemberViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.contact_deliver_invitee_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return invitees.size() + buildingMembers.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (invitees != null && invitees.size() > 0) {
            return VIEW_TYPE_INVITEE;
        } else if (buildingMembers != null && buildingMembers.size() > 0) {
            return VIEW_TYPE_MEMBER;
        }

        return VIEW_TYPE_INVITEE;
    }

    public void addItems(List<Invitee> listObjects) {
        invitees.clear();
        invitees.addAll(listObjects);
        notifyDataSetChanged();
    }

    public void addMemberItems(List<BuildingMember> listObjects) {
        buildingMembers.clear();
        buildingMembers.addAll(listObjects);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.contact_deliver_item_name_text)
        TextView mailText;

        @BindView(R.id.contact_deliver_item_checkbox)
        CheckBox itemCheckbox;

        @OnCheckedChanged(R.id.contact_deliver_item_checkbox)
        void checkClick(CompoundButton compoundButton, boolean checked) {

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

            Invitee currentInvitee = invitees.get(position);

            itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    checkedItemIds.add(invitees.get(getAdapterPosition()));
                    mCallback.onCheckChanged(checkedItemIds);
                } else {
                    if (checkedItemIds.contains(currentInvitee)) {
                        checkedItemIds.remove(checkedItemIds.indexOf(currentInvitee));
                    }
                    mCallback.onCheckChanged(checkedItemIds);
                }
            });

            mailText.setText(currentInvitee.getName());

            if (currentInvitee.getStatus().equalsIgnoreCase("pending")) {
                mailText.append(" (" + itemView.getContext().getString(R.string.pending).toLowerCase() + ")");
            }

        }
    }


    public class MemberViewHolder extends BaseViewHolder {

        @BindView(R.id.contact_deliver_item_name_text)
        TextView mailText;

        @BindView(R.id.contact_deliver_item_checkbox)
        CheckBox itemCheckbox;

        @OnCheckedChanged(R.id.contact_deliver_item_checkbox)
        void checkClick(CompoundButton compoundButton, boolean checked) {
            if (checked) {
                checkedBuildingMembers.add(buildingMembers.get(getAdapterPosition()));
                mCallback.onCheckChangedMember(checkedBuildingMembers);
            } else {
                if (checkedBuildingMembers.contains(checkedBuildingMembers.get(getAdapterPosition()))) {
                    checkedBuildingMembers.remove(
                            checkedBuildingMembers.indexOf(checkedBuildingMembers.get(getAdapterPosition())));
                }
                mCallback.onCheckChangedMember(checkedBuildingMembers);
            }
        }

        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            BuildingMember buildingMember = buildingMembers.get(position);

            mailText.setText(buildingMember.getSubscriberEmail());
        }
    }

    public interface Callback {

        void onCheckChanged(ArrayList<Invitee> invitees);

        void onCheckChangedMember(ArrayList<BuildingMember> buildingMembers);
    }
}
