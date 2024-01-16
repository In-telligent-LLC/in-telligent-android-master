package com.sca.in_telligent.ui.group.alert.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.ui.group.alert.detail.InboxNotificationTypeMapper;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;
import com.sca.in_telligent.util.CommonUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AlertListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    private final Context context;
    private Callback mCallback;
    private List<Notification> notifications;

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public interface Callback {
        void onAlertSelected(int i);

        void onDeleteAlert(Notification notification, int i);
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.profileImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.notification_profile_image, "field 'profileImage'", ImageView.class);
            viewHolder.titleText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_title_text, "field 'titleText'", TextView.class);
            viewHolder.infoText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_info_text, "field 'infoText'", TextView.class);
            viewHolder.descriptionText = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_description_text, "field 'descriptionText'", TextView.class);
            viewHolder.readImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.notification_read_image, "field 'readImage'", ImageView.class);
            viewHolder.trashImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.notification_trash_image, "field 'trashImage'", ImageView.class);
            viewHolder.typeImage = (ImageView) Utils.findRequiredViewAsType(view, (int) R.id.notification_type_image, "field 'typeImage'", ImageView.class);
            viewHolder.viewButton = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.notification_view_button, "field 'viewButton'", TextView.class);
        }

        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            viewHolder.profileImage = null;
            viewHolder.titleText = null;
            viewHolder.infoText = null;
            viewHolder.descriptionText = null;
            viewHolder.readImage = null;
            viewHolder.trashImage = null;
            viewHolder.typeImage = null;
            viewHolder.viewButton = null;
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class EmptyViewHolder_ViewBinding implements Unbinder {
        private EmptyViewHolder target;
        private View view7f080180;

        public EmptyViewHolder_ViewBinding(final EmptyViewHolder emptyViewHolder, View view) {
            this.target = emptyViewHolder;
            View findRequiredView = Utils.findRequiredView(view, (int) R.id.inbox_retry_button, "field 'retryButton' and method 'onRetryClick'");
            emptyViewHolder.retryButton = (Button) Utils.castView(findRequiredView, (int) R.id.inbox_retry_button, "field 'retryButton'", Button.class);
            this.view7f080180 = findRequiredView;
            findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListAdapter.EmptyViewHolder_ViewBinding.1
                public void doClick(View view2) {
                    emptyViewHolder.onRetryClick();
                }
            });
            emptyViewHolder.messageTextView = (TextView) Utils.findRequiredViewAsType(view, (int) R.id.inbox_no_message_text, "field 'messageTextView'", TextView.class);
        }

        public void unbind() {
            EmptyViewHolder emptyViewHolder = this.target;
            if (emptyViewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            emptyViewHolder.retryButton = null;
            emptyViewHolder.messageTextView = null;
            this.view7f080180.setOnClickListener(null);
            this.view7f080180 = null;
        }
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

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
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

        @Override // com.sca.in_telligent.ui.base.BaseViewHolder
        public void onBind(final int i) {
            super.onBind(i);
            final Notification notification = (Notification) AlertListAdapter.this.notifications.get(i);
            this.titleText.setText(notification.getTitle());
            InboxNotificationType map = InboxNotificationTypeMapper.map(notification);
            this.titleText.setTextColor(map.getColor());
            this.descriptionText.setText(notification.getDescription());
            this.typeImage.setImageResource(map.getIcon());
            this.infoText.setText(CommonUtils.getDateString(notification.getStartDate()) + ", " + AlertListAdapter.this.context.getResources().getString(map.getName()));
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListAdapter$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ViewHolder.this.m189xcadafd85(i, view);
                }
            });
            this.trashImage.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListAdapter$ViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ViewHolder.this.m190x3bb5e24(notification, i, view);
                }
            });
            if (notification.getNotificationBuilding() == null || notification.getNotificationBuilding().getImageUrl() == null) {
                return;
            }
            if (notification.getNotificationBuilding().getImageUrl() != null) {
                Picasso.get().load(notification.getNotificationBuilding().getImageUrl()).into(this.profileImage);
            } else {
                this.profileImage.setImageResource(CommonUtils.getDefaultImage(notification.getBuildingId()));
            }
        }

        /* renamed from: lambda$onBind$0$com-sca-in_telligent-ui-group-alert-list-AlertListAdapter$ViewHolder  reason: not valid java name */
        public /* synthetic */ void m189xcadafd85(int i, View view) {
            AlertListAdapter.this.mCallback.onAlertSelected(i);
        }

        /* renamed from: lambda$onBind$1$com-sca-in_telligent-ui-group-alert-list-AlertListAdapter$ViewHolder  reason: not valid java name */
        public /* synthetic */ void m190x3bb5e24(Notification notification, int i, View view) {
            AlertListAdapter.this.mCallback.onDeleteAlert(notification, i);
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
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
