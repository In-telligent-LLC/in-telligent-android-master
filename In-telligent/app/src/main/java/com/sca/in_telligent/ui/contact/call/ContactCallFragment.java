package com.sca.in_telligent.ui.contact.call;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.AnimationUtil;
import com.sca.in_telligent.util.twilio.AppTwilioUtil.TwilioUtilListener;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.twilio.voice.Call;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactCallFragment extends BaseFragment implements ContactCallMvpView,
        TwilioUtilListener {

    public static final int CHRONOMETER_BLINK_REPEAT_COUNT = 2;

    @Inject
    ContactCallMvpPresenter<ContactCallMvpView> mPresenter;

    @BindView(R.id.contact_call_status_image)
    ImageView callStatusImage;

    @BindView(R.id.dial_pad_layout)
    ConstraintLayout dialPad;

    @BindView(R.id.fab_start_call)
    FloatingActionButton floatingActionButtonStartCall;

    @BindView(R.id.fab_end_call)
    FloatingActionButton floatingActionButtonEndCall;

    @Inject
    TwilioUtil twilioUtil;

    @BindView(R.id.chronometer)
    Chronometer chronometer;

    public static final String TAG = "ContactCallFragment";

    private String buildingId = "";
    private boolean answered = false;
    private boolean active = false;

    private Call twilioCall;

    public static ContactCallFragment newInstance(String buildingId) {
        Bundle args = new Bundle();
        args.putString("buildingId", buildingId);
        ContactCallFragment fragment = new ContactCallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_call, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        buildingId = getArguments().getString("buildingId");
        initializeCallScreen();
    }

    private void initializeCallScreen() {
        dialPad.setVisibility(View.INVISIBLE);
        floatingActionButtonEndCall.hide();
        floatingActionButtonStartCall.show();

        twilioUtil.setListener(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        callStatusImage.setImageResource(R.drawable.call_status1);
    }

    @OnClick(R.id.fab_start_call)
    void startCallClick(View v) {
        mPresenter.requestRecordAudioPermission();
    }

    @OnClick(R.id.fab_end_call)
    void endCallClick(View v) {
        if (active) {
            answered = true;
            twilioUtil.endCall();
        }
    }

    @Override
    public void onDestroyView() {
        getBaseActivity().onFragmentDetached(TAG);
        mPresenter.onDetach();
        getActivity().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        super.onDestroyView();
    }

    @Override
    public void callDidFail() {
        if (twilioUtil != null) {
            twilioUtil.endCall();
        }

        if (!answered) {
            showPopup(getString(R.string.there_are_no_available_community_managers_consider));
        }else {
            showPopup(getString(R.string.there_was_error_connecting));
        }

        callDidEnd();
    }

    @Override
    public void callDidProgress(Call call) {
        if (!answered) {
            callStatusImage.setImageResource(R.drawable.call_status3);
        }
        dialPad.setVisibility(View.VISIBLE);
        this.twilioCall = call;
    }

    @Override
    public void callDidStart() {
        answered = true;
        active = true;
        callStatusImage.setImageResource(R.drawable.call_status4);
        chronometer.start();
        floatingActionButtonStartCall.hide();
        floatingActionButtonEndCall.show();
    }

    @Override
    public void callDidEnd() {
        active = false;
        if (chronometer != null) {
            chronometer.stop();
            animateChronometerReset();
        }

        twilioUtil.setListener(null);
        twilioCall = null;
        dialPad.setVisibility(View.INVISIBLE);
        floatingActionButtonStartCall.show();
        initializeCallScreen();
        Toast.makeText(getContext(), R.string.call_ended, Toast.LENGTH_LONG).show();
    }

    private void animateChronometerReset() {
        AnimationUtil.blink(chronometer, CHRONOMETER_BLINK_REPEAT_COUNT, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onDetach() {
        if (twilioUtil != null) {
            twilioUtil.endCall();
        }
        super.onDetach();
    }

    @Override
    public void recordAudioPermissionResult(boolean granted) {
        if (granted) {
            twilioUtil.makeCall(buildingId);
            callStatusImage.setImageResource(R.drawable.call_status2);
        }
    }

    @OnClick({R.id.dialOneLayout, R.id.dialTwoLayout, R.id.dialThreeLayout,
            R.id.dialFourLayout, R.id.dialFiveLayout, R.id.dialSixLayout,
            R.id.dialSevenLayout, R.id.dialEightLayout, R.id.dialNineLayout})
    void onDialPadClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (twilioCall == null) {
            Log.e(TAG, "Cannot perform the dial action because the Twilio call object is null");
            return;
        }

        switch (view.getId()) {
            case R.id.dialOneLayout:
                twilioCall.sendDigits("1");
                break;
            case R.id.dialTwoLayout:
                twilioCall.sendDigits("2");
                break;
            case R.id.dialThreeLayout:
                twilioCall.sendDigits("3");
                break;
            case R.id.dialFourLayout:
                twilioCall.sendDigits("4");
                break;
            case R.id.dialFiveLayout:
                twilioCall.sendDigits("5");
                break;
            case R.id.dialSixLayout:
                twilioCall.sendDigits("6");
                break;
            case R.id.dialSevenLayout:
                twilioCall.sendDigits("7");
                break;
            case R.id.dialEightLayout:
                twilioCall.sendDigits("8");
                break;
            case R.id.dialNineLayout:
                twilioCall.sendDigits("9");
                break;
        }
    }
}
