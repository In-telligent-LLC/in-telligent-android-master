package com.sca.in_telligent.ui.notificationdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.openapi.data.network.model.NotificationAttachment;
import com.sca.in_telligent.ui.base.BaseViewHolder;
import com.sca.in_telligent.util.VideoDownloader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class NotificationAttachmentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final String ATTACHMENT_TYPE_IMAGE = "image";
    public static final String ATTACHMENT_TYPE_VIDEO = "video";
    public static final String ATTACHMENT_TYPE_PDF = "pdf";

    public void setVideoDownloader(VideoDownloader videoDownloader) {
        this.videoDownloader = videoDownloader;
    }

    VideoDownloader videoDownloader;

    private final List<NotificationAttachment> notificationAttachments;
    private Callback mCallback;
    private final Context mContext;

    public void setActivityContext(Context activityContext) {
        this.activityContext = activityContext;
    }

    private Context activityContext;

    public NotificationAttachmentAdapter(@ApplicationContext Context context,
                                         List<NotificationAttachment> notificationAttachments) {
        this.notificationAttachments = notificationAttachments;
        mContext = context;
    }

    public void setCallback(NotificationAttachmentAdapter.Callback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAttachmentAdapter.ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.notification_attachment_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return notificationAttachments.size();
    }

    public void addItems(List<NotificationAttachment> listObjects) {
        notificationAttachments.addAll(listObjects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.notification_attachment_document_title)
        TextView documentTitle;

        @BindView(R.id.notification_attachment_thumbnail_image_container)
        ViewGroup thumbnailContainer;

        @BindView(R.id.notification_attachment_thumbnail_image)
        ImageView thumbnailImage;


        @BindView(R.id.imageview_document_type)
        ImageView imageViewDocumentType;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            documentTitle = itemView.findViewById(R.id.notification_attachment_document_title);
            thumbnailContainer = itemView.findViewById(R.id.notification_attachment_thumbnail_image_container);
            thumbnailImage = itemView.findViewById(R.id.notification_attachment_thumbnail_image);
            imageViewDocumentType = itemView.findViewById(R.id.imageview_document_type);
        }

        @Override
        protected void clear() {

        }

        public void onBind(final int position) {
            super.onBind(position);

            NotificationAttachment attachment = notificationAttachments.get(position);
            imageViewDocumentType.setVisibility(View.GONE);
            thumbnailContainer.setVisibility(View.VISIBLE);


            documentTitle.setText(attachment.getFilename());

            switch (attachment.getType()) {
                case ATTACHMENT_TYPE_IMAGE:
                    if (attachment.getUrl() != null) {

                        if (attachment.getUrl().endsWith(".gif")) {
                            Glide.with(itemView.getContext()).asGif()
                                    .load(attachment.getUrl()).into(thumbnailImage);
                        } else {
                            Glide.with(itemView.getContext())
                                    .load(attachment.getUrl())
                                    .apply(RequestOptions.timeoutOf(5 * 60 * 1000))
                                    .into(thumbnailImage);
                        }

                        itemView.setOnClickListener(
                                view -> mCallback.onAttachmentClicked(position, attachment.getType()));
                    }
                    break;
                case ATTACHMENT_TYPE_VIDEO:
                    videoDownloader.download(attachment.getUrl(), attachment.getFilename())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(file -> {
                        Bitmap thumbnail = ThumbnailUtils
                                .createVideoThumbnail(Uri.fromFile(file).getPath(),
                                        MediaStore.Video.Thumbnails.MINI_KIND);
                        thumbnailImage.setImageBitmap(thumbnail);
                        itemView.setOnClickListener(
                                view -> mCallback.onVideoAttachmentClicked(file.getPath(), attachment.getType()));

                    }, throwable -> System.out.println(throwable.getMessage()));
                    break;
                case ATTACHMENT_TYPE_PDF:
                    thumbnailContainer.setVisibility(View.GONE);
                    imageViewDocumentType.setVisibility(View.VISIBLE);
                    itemView.setOnClickListener(view -> mCallback.onDocumentClicked(attachment.getUrl(),
                            attachment.getType()));
                    break;
            }
        }
    }

    public interface Callback {

        void onAttachmentClicked(int position, String type);

        void onVideoAttachmentClicked(String path, String type);

        void onDocumentClicked(String url, String type);
    }
}
