package com.sca.in_telligent.ui.preview;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseDialog;
import javax.inject.Inject;

public class MessageViewDialog extends BaseDialog implements MessageViewMvpView {

  public interface PushNotificationDetailCallback {
    void onShowNotificationDetails(Notification notification);
  }

  private static final String TAG = "MessageViewDialog";

  private PushNotification pushNotification;

  private PushNotificationDetailCallback pushNotificationDetailCallback;

  @Inject
  MessageViewMvpPresenter<MessageViewMvpView> mPresenter;

  @BindView(R.id.message_preview_body_text)
  TextView bodyText;

  @BindView(R.id.message_preview_title_text)
  TextView titleText;

  @BindView(R.id.message_view_ok_button)
  TextView okButton;

  public static MessageViewDialog newInstance(PushNotification pushNotification) {
    MessageViewDialog fragment = new MessageViewDialog();
    Bundle bundle = new Bundle();
    bundle.putSerializable("pushNotification", pushNotification);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    pushNotificationDetailCallback = (PushNotificationDetailCallback) context;
    pushNotification = (PushNotification) getArguments().getSerializable("pushNotification");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.message_view_dialog, container, false);

    ActivityComponent component = getActivityComponent();
    if (component != null) {

      component.inject(this);

      setUnBinder(ButterKnife.bind(this, view));

      mPresenter.onAttach(this);
    }
    return view;
  }

  @OnClick(R.id.message_view_ok_button)
  void okClicked(View v) {
    mPresenter.onDetach();
    dismissDialog(TAG);
  }

  @OnClick(R.id.message_view_see_full_message_button)
  void seeFullMessageClicked(View v) {
    mPresenter.getNotification(pushNotification.getNotificationId());
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    mPresenter.onDetach();
    super.onDestroyView();
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }

  @Override
  protected void setUp(View view) {
    if (pushNotification != null) {
      titleText.setText(pushNotification.getTitle());
      bodyText.setText(pushNotification.getBody());
    }

  }

  @Override
  public void notifyNotificationDetailsFetched(Notification notification) {
    mPresenter.onDetach();
    dismissDialog(TAG);
    if (pushNotificationDetailCallback != null) {
      pushNotificationDetailCallback.onShowNotificationDetails(notification);
    }
  }
}
