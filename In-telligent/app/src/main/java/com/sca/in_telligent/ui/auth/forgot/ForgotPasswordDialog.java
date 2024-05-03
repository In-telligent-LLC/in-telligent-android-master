package com.sca.in_telligent.ui.auth.forgot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.ForgotPasswordRequest;
import com.sca.in_telligent.ui.base.BaseDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordDialog extends BaseDialog implements ForgotPasswordMvpView {

  private static final String TAG = "ForgotPasswordDialog";

  @Inject
  ForgotPasswordMvpPresenter<ForgotPasswordMvpView> mPresenter;

  @BindView(R.id.forgotPasswordEditText)
  EditText forgotPasswordEmail;

  TextView forgotPasswordSubmit;

  TextView forgotPasswordCancel;

  public static ForgotPasswordDialog newInstance() {
    ForgotPasswordDialog fragment = new ForgotPasswordDialog();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.forgot_password_dialog, container, false);

    ActivityComponent component = getActivityComponent();
    if (component != null) {

      component.inject(this);
      forgotPasswordEmail = view.findViewById(R.id.forgotPasswordEditText);
      forgotPasswordSubmit = view.findViewById(R.id.forgotPasswordSubmit);
      forgotPasswordCancel = view.findViewById(R.id.forgotPasswordCancel);

      setUnBinder(ButterKnife.bind(this, view));

      forgotPasswordSubmit.setOnClickListener(v -> forgotPasswordSubmit());

      forgotPasswordCancel.setOnClickListener(this::cancelClick);

      mPresenter.onAttach(this);


    }

    return view;
  }

  @OnClick(R.id.forgotPasswordCancel)
  void cancelClick(View v) {
    dismissDialog(TAG);
  }

  @OnClick(R.id.forgotPasswordSubmit)
  void forgotPasswordSubmit() {
    ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
    forgotPasswordRequest.setEmail(forgotPasswordEmail.getText().toString());
    mPresenter.submitEmail(forgotPasswordRequest);
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }


  @Override
  protected void setUp(View view) {

  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }

  @Override
  public void showSubmitToast() {
    showMessage(R.string.check_your_email_for_reset_link);
    mPresenter.onDetach();
    dismissDialog(TAG);
  }
}
