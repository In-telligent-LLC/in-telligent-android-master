package com.sca.in_telligent.ui.inbox;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.ui.group.alert.detail.InboxNotificationTypeMapper;
import com.sca.in_telligent.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<Notification> notifications;
    private Callback mCallback;
    private final Context context;

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public InboxAdapter(Context context, List<Notification> notifications) {
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
                return new InboxAdapter.ViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.inbox_notification_item_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new InboxAdapter.EmptyViewHolder(
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
        if (notifications != null && notifications.size() > 1) {
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
            notifications.clear();
        }
        notifications.addAll(listObjects);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        notifications.remove(position);
        notifyItemRemoved(position);
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
            profileImage = itemView.findViewById(R.id.notification_profile_image);
            titleText = itemView.findViewById(R.id.notification_title_text);
            infoText = itemView.findViewById(R.id.notification_info_text);
            descriptionText = itemView.findViewById(R.id.notification_description_text);
            readImage = itemView.findViewById(R.id.notification_read_image);
            trashImage = itemView.findViewById(R.id.notification_trash_image);
            typeImage = itemView.findViewById(R.id.notification_type_image);
            viewButton = itemView.findViewById(R.id.notification_view_button);
        }

        @Override
        protected void clear() {

        }

        @SuppressLint("SetTextI18n")
        public void onBind(final int position) {
            super.onBind(position);
            Notification notification = notifications.get(position);


            View.OnClickListener onClickListener = v -> {
                if (notification.isSaved()) {
                    mCallback.onSavedMessageSelected(getAdapterPosition(), notification);
                } else {
                    mCallback.onNotificationSelected(getAdapterPosition(), notification);
                }

            };

            itemView.setOnClickListener(onClickListener);
            viewButton.setOnClickListener(onClickListener);

            trashImage.setOnClickListener(v -> mCallback.onAlertDelete(notification, getAdapterPosition()));

            titleText.setText(notification.getTitle());

            InboxNotificationType inboxNotificationType = InboxNotificationTypeMapper.map(notification);

            titleText.setTextColor(inboxNotificationType.getColor());

            if (inboxNotificationType == InboxNotificationType.CRITICAL) {
                titleText.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.nugget, null));
            } else if (inboxNotificationType == InboxNotificationType.LIFE_SAFETY) {
                titleText.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.thunderbird, null));
            } else {
                titleText.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.black, null));
            }

            descriptionText.setText(notification.getDescription());

            typeImage.setVisibility(View.GONE);

            if (notification.getNotificationSubscriber() != null
                    && notification.getNotificationSubscriber().getOpened() != null) {
                readImage.setVisibility(View.GONE);
            } else if (notification.isOpened()) {
                readImage.setVisibility(View.GONE);
            } else {
                readImage.setVisibility(View.VISIBLE);
            }

            infoText.setText(
                    CommonUtils.getDateString(notification.getStartDate()) + ", " + context.getResources()
                            .getString(inboxNotificationType.getName()));

            if (notification.getNotificationBuilding() != null) {
                String imageurl = notification.getNotificationBuilding().getImageUrl();
                if (imageurl != null && !imageurl.isEmpty()) {
                    Glide.with(itemView.getContext()).load(imageurl)
                            .thumbnail(0.1f)
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
            retryButton = itemView.findViewById(R.id.inbox_retry_button);
            messageTextView = itemView.findViewById(R.id.inbox_no_message_text);

            retryButton.setOnClickListener(v -> onRetryClick());
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

        void onNotificationSelected(int position, Notification notification);

        void onSavedMessageSelected(int position, Notification notification);

        void onAlertDelete(Notification notification, int position);
    }
}
