package com.sca.in_telligent.ui.group.alert.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.ui.group.alert.detail.InboxNotificationTypeMapper;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Notification> notifications;
    private Callback mCallback;
    private Context context;

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public AlertListAdapter(Context context, List<Notification> notifications) {
        this.notifications = notifications;
        this.context = context;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new AlertListAdapter.ViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.inbox_notification_item_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new AlertListAdapter.EmptyViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.inbox_notification_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (notifications != null && notifications.size() > 0) {
            return notifications.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (notifications != null && notifications.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addItems(List<Notification> listObjects, int page) {
        if (page == 0) {
            notifications = new ArrayList<Notification>();
        }
        notifications.addAll(listObjects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.notification_profile_image)
        ImageView profileImage;

        @BindView(R.id.notification_title_text)
        TextView titleText;

        @BindView(R.id.notification_info_text)
        TextView infoText;

        @BindView(R.id.notification_description_text)
        TextView descriptionText;

        @BindView(R.id.notification_read_image)
        ImageView readImage;

        @BindView(R.id.notification_trash_image)
        ImageView trashImage;

        @BindView(R.id.notification_type_image)
        ImageView typeImage;

        @BindView(R.id.notification_view_button)
        TextView viewButton;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            Notification notification = notifications.get(position);
            titleText.setText(notification.getTitle());

            InboxNotificationType inboxNotificationType = InboxNotificationTypeMapper.map(notification);

            titleText.setTextColor(inboxNotificationType.getColor());

            descriptionText.setText(notification.getDescription());

            typeImage.setImageResource(inboxNotificationType.getIcon());

            infoText.setText(
                    CommonUtils.getDateString(notification.getStartDate()) + ", " + context.getResources()
                            .getString(inboxNotificationType.getName()));

            itemView.setOnClickListener(view -> mCallback.onAlertSelected(position));

            trashImage.setOnClickListener(v -> {
                mCallback.onDeleteAlert(notification, position);
            });

            if (notification.getNotificationBuilding() != null
                    && notification.getNotificationBuilding().getImageUrl() != null) {
                String imageurl = notification.getNotificationBuilding().getImageUrl();
                if (imageurl != null) {
                    Picasso.get().load(notification.getNotificationBuilding().getImageUrl())
                            .into(profileImage);
                } else {
                    profileImage.setImageResource(CommonUtils.getDefaultImage(notification.getBuildingId()));
                }
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.inbox_retry_button)
        Button retryButton;

        @BindView(R.id.inbox_no_message_text)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.inbox_retry_button)
        void onRetryClick() {
            if (mCallback != null) {
            }
        }
    }

    public interface Callback {

        void onDeleteAlert(Notification notification, int position);

        void onAlertSelected(int position);
    }
}
