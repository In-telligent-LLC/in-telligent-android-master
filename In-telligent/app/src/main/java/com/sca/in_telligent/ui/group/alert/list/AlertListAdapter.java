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
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    private final Context context;
    private Callback mCallback;
    private List<Notification> notifications;

    public interface Callback {
        void onAlertSelected(int i);

        void onDeleteAlert(Notification notification, int i);
    }

    public AlertListAdapter(Context context, List<Notification> list) {
        this.notifications = list;
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_notification_item_view, viewGroup, false));
        }
        return new EmptyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_notification_empty_view, viewGroup, false));
    }

    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    public int getItemCount() {
        List<Notification> list = this.notifications;
        if (list == null || list.size() <= 0) {
            return 1;
        }
        return this.notifications.size();
    }

    public int getItemViewType(int i) {
        List<Notification> list = this.notifications;
        return (list == null || list.size() <= 0) ? 0 : 1;
    }

    public void addItems(List<Notification> list, int i) {
        if (i == 0) {
            this.notifications = new ArrayList();
        }
        this.notifications.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.notification_description_text)
        TextView descriptionText;
        @BindView(R.id.notification_info_text)
        TextView infoText;
        @BindView(R.id.notification_profile_image)
        ImageView profileImage;
        @BindView(R.id.notification_read_image)
        ImageView readImage;
        @BindView(R.id.notification_title_text)
        TextView titleText;
        @BindView(R.id.notification_trash_image)
        ImageView trashImage;
        @BindView(R.id.notification_type_image)
        ImageView typeImage;
        @BindView(R.id.notification_view_button)
        TextView viewButton;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
        @BindView(R.id.inbox_no_message_text)
        TextView messageTextView;
        @BindView(R.id.inbox_retry_button)
        Button retryButton;

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        protected void clear() {
        }

        public EmptyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.inbox_retry_button})
        void onRetryClick() {
            Callback unused = AlertListAdapter.this.mCallback;
        }
    }
}
