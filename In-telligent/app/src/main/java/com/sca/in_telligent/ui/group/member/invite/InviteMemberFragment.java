package com.sca.in_telligent.ui.group.member.invite;

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
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.InviteOthersRequest;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteMemberFragment extends BaseFragment implements InviteMemberMvpView {

  @Inject
  InviteMemberMvpPresenter<InviteMemberMvpView> mPresenter;

  public static final String TAG = "InviteMemberFragment";

  @BindView(R.id.invite_member_name_edittext)
  EditText nameEdittext;

  @BindView(R.id.invite_member_mail_edittext)
  EditText mailEdittext;

  @BindView(R.id.invite_member_phone_edittext)
  EditText phoneEdittext;

  @BindView(R.id.invite_member_send)
  TextView sendInvite;

  private String buildingId;

  public interface InviteMemberSelector {

  }

  InviteMemberSelector inviteMemberSelector;

  public static InviteMemberFragment newInstance(String buildingId) {
    Bundle args = new Bundle();
    args.putString("buildingId", buildingId);
    InviteMemberFragment fragment = new InviteMemberFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    inviteMemberSelector = (InviteMemberSelector) context;
    buildingId = getArguments().getString("buildingId");
  }

  @Override
  protected void setUp(View view) {

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_invite_member, container, false);
    ActivityComponent component = getActivityComponent();
    if (component != null) {
      component.inject(this);
      setUnBinder(ButterKnife.bind(this, view));
      mPresenter.onAttach(this);
    }

    return view;
  }

  @OnClick(R.id.invite_member_send)
  void sendInviteClick(View v) {
    if (nameEdittext.getText().toString().length() > 3
        && mailEdittext.getText().toString().length() > 3
        || phoneEdittext.getText().toString().length() > 3) {
      Invitee invitee = new Invitee();

      invitee.setName(nameEdittext.getText().toString());
      invitee.setEmail(mailEdittext.getText().toString());
      invitee.setPhone(
          phoneEdittext.getText().toString().length() > 0 ? phoneEdittext.getText().toString()
              : "0");

      ArrayList<Invitee> invitees = new ArrayList<>();
      invitees.add(invitee);

      InviteOthersRequest inviteOthersRequest = new InviteOthersRequest();
      inviteOthersRequest.setInvitees(invitees);

      mPresenter.sendInvite(buildingId, inviteOthersRequest);
    } else {

    }

  }

  @Override
  public void inviteResult(boolean success) {
    if (success) {
      showMessage(getResources().getString(R.string.invited));
      getActivity().onBackPressed();
    }
  }

  @Override
  public void onDestroyView() {
    mPresenter.onDetach();
    super.onDestroyView();
  }
}
