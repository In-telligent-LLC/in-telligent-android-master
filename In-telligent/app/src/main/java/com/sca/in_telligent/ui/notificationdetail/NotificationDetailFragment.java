package com.sca.in_telligent.ui.notificationdetail;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;


import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.EditSaveMessageRequest;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.NotificationAttachment;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.TranslationResponse;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.group.alert.detail.InboxNotificationTypeMapper;
import com.sca.in_telligent.ui.inbox.AttachmentPreviewDialog;
import com.sca.in_telligent.ui.inbox.InboxFragment;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;
import com.sca.in_telligent.util.CommonUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public class NotificationDetailFragment extends BaseFragment implements NotificationDetailMvpView,
        NotificationAttachmentAdapter.Callback, OnInitListener {

    public enum Source {
        INBOX,
        PUSH_NOTIFICATION
    }

    private Source source;

    public static final String TAG = "NotificationDetailFragment";

    @Inject
    NotificationDetailMvpPresenter<NotificationDetailMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    NotificationAttachmentAdapter attachmentAdapter;

    @BindView(R.id.notification_detail_title_text)
    TextView notificationTitleText;

    @BindView(R.id.notification_detail_info_text)
    TextView notificationInfoText;

    @BindView(R.id.notification_detail_attachment_text)
    TextView notificationAttachmentText;

    @BindView(R.id.notification_detail_description_text)
    TextView notificationDescriptionText;

    @BindView(R.id.notification_detail_attachment_list)
    RecyclerView notificationAttachmentList;

    @BindView(R.id.notification_detail_back_layout)
    RelativeLayout backLayout;

    @BindView(R.id.notification_detail_translate)
    TextView translateButton;

    @BindView(R.id.notification_detail_tts)
    TextView ttsButton;

    @BindView(R.id.notification_detail_save)
    TextView saveButton;

    @BindView(R.id.notification_detail_left_arrow)
    ImageView previousButton;

    @BindView(R.id.notification_detail_right_arrow)
    ImageView nextButton;

    private ArrayList<NotificationAttachment> attachments = new ArrayList<>();

    private TextToSpeech tts;

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {

        }
    }

    public interface NotificationDetailCallback {

        void nextClick(int pos, int lastIndex, boolean saved);

        void previousClick(int pos, int lastIndex, boolean saved);
    }

    private NotificationDetailCallback notificationDetailCallback;

    private Notification notification;
    private int position;
    private int lastIndex;

    private ArrayList<NotificationLanguage> languages = new ArrayList<>();
    private String languageValue = "";

    public static NotificationDetailFragment newInstance(Notification notification, int position,
                                                         int lastIndex) {
        Bundle args = new Bundle();
        args.putParcelable("notification", notification);
        args.putInt("pos", position);
        args.putInt("lastIndex", lastIndex);
        NotificationDetailFragment fragment = new NotificationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static NotificationDetailFragment newInstance(Notification notification) {
        Bundle args = new Bundle();
        args.putParcelable("notification", notification);
        NotificationDetailFragment fragment = new NotificationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notification = getArguments().getParcelable("notification");
        position = getArguments().getInt("pos", -1);
        lastIndex = getArguments().getInt("lastIndex", -1);

        if (position == -1 && lastIndex == -1) {
            source = Source.PUSH_NOTIFICATION;
        } else {
            source = Source.INBOX;
        }


        notificationDetailCallback = (NotificationDetailCallback) context;
        tts = new TextToSpeech(getContext(), this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_detail, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            notificationTitleText = view.findViewById(R.id.notification_detail_title_text);
            notificationInfoText = view.findViewById(R.id.notification_detail_info_text);
            notificationAttachmentText = view.findViewById(R.id.notification_detail_attachment_text);
            notificationDescriptionText = view.findViewById(R.id.notification_detail_description_text);
            notificationAttachmentList = view.findViewById(R.id.notification_detail_attachment_list);
            backLayout = view.findViewById(R.id.notification_detail_back_layout);
            translateButton = view.findViewById(R.id.notification_detail_translate);
            ttsButton = view.findViewById(R.id.notification_detail_tts);
            saveButton = view.findViewById(R.id.notification_detail_save);
            previousButton = view.findViewById(R.id.notification_detail_left_arrow);
            nextButton = view.findViewById(R.id.notification_detail_right_arrow);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

            backLayout.setOnClickListener(v -> backClick(v));
            saveButton.setOnClickListener(v -> editSave(v));
            translateButton.setOnClickListener(v -> translateClick(v));
            ttsButton.setOnClickListener(v -> textToSpeechClick(v));
            nextButton.setOnClickListener(v -> nextNotification(v));
            previousButton.setOnClickListener(v -> previousNotification(v));

        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        if (notification != null) {
            notificationTitleText.setText(notification.getTitle());
            notificationDescriptionText.setText(notification.getDescription());

            attachments = notification.getNotificationAttachments();
            if (attachments.size() > 0) {
                Observable.fromIterable(attachments)
                        .filter((@SuppressLint("CheckResult") NotificationAttachment notificationAttachment) -> !notificationAttachment.getType().equals("unknown"))
                        .toList()
                        .subscribe(
                                (notificationAttachments, throwable) -> attachments = (ArrayList<NotificationAttachment>) notificationAttachments);
            }

            String attachmentString = getString(R.string.attachment_1_total,
                    attachments.size());

            InboxNotificationType inboxNotificationType = InboxNotificationTypeMapper.map(notification);
            notificationAttachmentText.setText(attachmentString);
            notificationInfoText.setText(
                    CommonUtils.getDateString(notification.getStartDate()) + ", " + getString(inboxNotificationType.getName()));

            saveButton.setText(notification.isSaved() ? getString(R.string.unsave_message)
                    : getString(R.string.save_message));


            //Make sure that links work
            notificationDescriptionText.setMovementMethod(LinkMovementMethod.getInstance());
            Linkify.addLinks(notificationDescriptionText, Linkify.ALL);

            mPresenter.listLanguages();
            initAttachments();

            setUpArrowsVisibility(source);
        }
    }

    private void setUpArrowsVisibility(Source source) {
        switch (source) {
            case INBOX:
                previousButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                break;
            case PUSH_NOTIFICATION:
                previousButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                break;
        }
    }

    private void initAttachments() {
        if (attachments.size() > 0) {
            notificationAttachmentList.setVisibility(View.VISIBLE);
            attachmentAdapter.setCallback(this);
            attachmentAdapter.setActivityContext(getActivity());
            attachmentAdapter.setVideoDownloader(getVideoDownloader());
            attachmentAdapter.addItems(attachments);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            notificationAttachmentList.setLayoutManager(mLayoutManager);
            notificationAttachmentList.setAdapter(attachmentAdapter);
        } else {
            notificationAttachmentList.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.notification_detail_back_layout)
    void backClick(View v) {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.notification_detail_left_arrow)
    void previousNotification(View v) {
        if (position > 0) {
            getBaseActivity().onFragmentDetached(TAG);
            position = position - 1;
            notificationDetailCallback.previousClick(position, lastIndex, notification.isSaved());
        }
    }

    @OnClick(R.id.notification_detail_right_arrow)
    void nextNotification(View v) {
        if (position < lastIndex - 1) {
            getBaseActivity().onFragmentDetached(TAG);
            position = position + 1;
            notificationDetailCallback.nextClick(position, lastIndex, notification.isSaved());
        }
    }

    @OnClick(R.id.notification_detail_save)
    void editSave(View v) {
        EditSaveMessageRequest editSaveMessageRequest = new EditSaveMessageRequest();

        if (notification.isSaved()) {
            editSaveMessageRequest.setAction("remove");
        } else {
            editSaveMessageRequest.setAction("add");
        }
        editSaveMessageRequest.setNotificationId(Integer.toString(notification.getId()));

        mPresenter.editSavedMessage(editSaveMessageRequest);
    }

    @Override
    public void onAttachmentClicked(int position, String type) {
        AttachmentPreviewDialog
                .newInstance(attachments.get(position).getUrl(), type)
                .show(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onVideoAttachmentClicked(String path, String type) {
        AttachmentPreviewDialog
                .newInstance(path, type)
                .show(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onDocumentClicked(String url, String type) {
        CommonUtils.openPdfFile(getContext(), url);
    }

    @Override
    public void saveButtonChange(String operation) {
        InboxFragment inboxFragment = (InboxFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(InboxFragment.TAG);

        if (operation.equals("add")) {
            saveButton.setText(R.string.unsave_message);
            inboxFragment.getNotifications().get(position).setSaved(true);
        } else {
            saveButton.setText(R.string.save_message);
            inboxFragment.getNotifications().get(position).setSaved(false);
        }

        inboxFragment.reloadSavedMessages();
    }

    @OnClick(R.id.notification_detail_translate)
    void translateClick(View v) {
        String[] languageOptions = new String[languages.size()];
        int i = 0;
        for (NotificationLanguage l : languages) {
            languageOptions[i++] = l.getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle(R.string.translate_to)
                .setItems(languageOptions, (dialogInterface, pos) -> {
                    languageValue = languages.get(pos).getLanguage();
                    mPresenter.getTranslatedAlert(Integer.toString(notification.getId()), languageValue);
                })
                .show();
    }

    @OnClick(R.id.notification_detail_tts)
    void textToSpeechClick(View v) {
        if (tts != null) {
            tts.setOnUtteranceProgressListener(mProgressListener);

            if (tts.isSpeaking()) {
                tts.stop();
                ttsButton.setText(getString(R.string.text_to_speech));
            } else {
                ttsButton.setText(getString(R.string.stop_speaking));
                tts.speak(notification.getTitle() + " " + notification.getDescription(),
                        TextToSpeech.QUEUE_FLUSH, null, "");
            }

        }
    }

    @Override
    public void loadLanguages(ArrayList<NotificationLanguage> notificationLanguages) {
        languages = notificationLanguages;
    }

    @Override
    public void translatedAlert(TranslationResponse translationResponse) {
        if (translationResponse != null && translationResponse.getTitle() != null) {
            notificationTitleText.setText(translationResponse.getTitle());
        }
        if (translationResponse != null & translationResponse.getBody() != null) {
            notificationDescriptionText.setText(translationResponse.getBody());
        }
    }

    @Override
    public void onDestroyView() {
        if (tts.isSpeaking()) {
            tts.stop();
            ttsButton.setText(getString(R.string.text_to_speech));
        }
        tts.shutdown();
        mPresenter.onDetach();
        super.onDestroyView();
    }

    private final UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onError(String utteranceId) {
            showMessage(getString(R.string.there_was_an_error_playing_the_alert));
        }

        @Override
        public void onDone(String utteranceId) {
            ttsButton.setText(getString(R.string.text_to_speech));
        }
    };
}
