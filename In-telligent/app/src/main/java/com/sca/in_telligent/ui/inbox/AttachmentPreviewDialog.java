package com.sca.in_telligent.ui.inbox;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseDialog;
import com.sca.in_telligent.util.ZoomableImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttachmentPreviewDialog extends BaseDialog {

  private static final String TAG = "AttachmentPreviewDialog";

  @BindView(R.id.attachment_preview_image)
  ZoomableImageView attachmentImage;

  @BindView(R.id.attachment_preview_close)
  ImageView attachmentClose;

  @BindView(R.id.attachment_preview_videoView)
  VideoView videoView;

  private String link;
  private String type;

  public static AttachmentPreviewDialog newInstance(String link, String type) {
    AttachmentPreviewDialog fragment = new AttachmentPreviewDialog();
    Bundle bundle = new Bundle();
    bundle.putString("link", link);
    bundle.putString("type", type);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.attachment_preview_dialog, container, false);

    ActivityComponent component = getActivityComponent();
    if (component != null) {

      component.inject(this);

      setUnBinder(ButterKnife.bind(this, view));
    }
    return view;
  }

  @Override
  protected void setUp(View view) {
    link = getArguments().getString("link");
    type = getArguments().getString("type");
    if (type.equals("image")) {
      attachmentImage.setVisibility(View.VISIBLE);
      videoView.setVisibility(View.GONE);
      if (!TextUtils.isEmpty(link)) {

        if (link.endsWith(".gif")) {
          Glide.with(getContext()).asGif()
                  .load(link).into(attachmentImage);
        } else {
          Glide.with(getContext())
                  .load(link).into(attachmentImage);
        }
      }
    } else if (type.equals("video")) {
      attachmentImage.setVisibility(View.GONE);
      videoView.setVisibility(View.VISIBLE);
      videoView.setVideoURI(Uri.fromFile(new File(link)));
      MediaController mediaController = new MediaController(getContext());
      mediaController.setMediaPlayer(videoView);
      videoView.start();
    }
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }

  @OnClick(R.id.attachment_preview_close)
  void close(View v) {
    dismiss();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

}
