package com.sca.in_telligent.ui.group.member.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.EditCommunityInviteeRequest;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditMemberFragment extends BaseFragment implements EditMemberMvpView {

  @Inject
  EditMemberMvpPresenter<EditMemberMvpView> mPresenter;

  @BindView(R.id.edit_member_name_edittext)
  EditText nameEdittext;

  @BindView(R.id.edit_member_mail_edittext)
  EditText mailEdittext;

  @BindView(R.id.edit_member_phone_edittext)
  EditText phoneEdittext;

  @BindView(R.id.edit_member_save_changes)
  TextView saveButton;

  private Invitee invitee;

  public static final String TAG = "EditMemberFragment";

  public interface EditCommunityMemberCallback {
    void onCommunityMembersUpdated();
  }

  EditCommunityMemberCallback editCommunityMemberCallback;

  public static EditMemberFragment newInstance(Invitee invitee) {
    Bundle args = new Bundle();
    args.putSerializable("invitee", invitee);
    EditMemberFragment fragment = new EditMemberFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    editCommunityMemberCallback = (EditCommunityMemberCallback) context;
    invitee = (Invitee) getArguments().getSerializable("invitee");
  }

  @Override
  protected void setUp(View view) {
    if (invitee != null) {
      nameEdittext.setText(invitee.getName());
      mailEdittext.setText(invitee.getEmail());
      phoneEdittext.setText(invitee.getPhone());
    }
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_edit_member, container, false);
    ActivityComponent component = getActivityComponent();
    if (component != null) {
      component.inject(this);
      setUnBinder(ButterKnife.bind(this, view));
      mPresenter.onAttach(this);
    }

    return view;
  }

  @OnClick(R.id.edit_member_save_changes)
  void saveClick(View v) {

    clearFieldErrors();

    String name = nameEdittext.getText().toString();

    if (name.isEmpty()) {
      nameEdittext.setError("Name cannot be empty");
      return;
    }

    String email = mailEdittext.getText().toString();
    if (!Utils.isValidEmail(email)) {
      mailEdittext.setError("Email is invalid");
      return;
    }

    String phone = phoneEdittext.getText().toString();

    if (phone.isEmpty()) {
      phoneEdittext.setError("Phone cannot be empty");
      return;
    }

    EditCommunityInviteeRequest editCommunityInviteeRequest = new EditCommunityInviteeRequest(invitee.getId(), name, email, phone);
    mPresenter.saveMemberEdit(editCommunityInviteeRequest);
  }

  private void clearFieldErrors() {
    nameEdittext.setError(null);
    mailEdittext.setError(null);
    phoneEdittext.setError(null);
  }

  @Override
  public void editResult(boolean success) {
    if (success) {
      showMessage(getResources().getString(R.string.saved));
      getActivity().onBackPressed();

      if (editCommunityMemberCallback != null) {
        editCommunityMemberCallback.onCommunityMembersUpdated();
      }
    }
  }

  @OnClick({R.id.edit_member_back_button, R.id.edit_member_back_text})
  void backClick(View v) {
    getActivity().onBackPressed();
  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }

}
